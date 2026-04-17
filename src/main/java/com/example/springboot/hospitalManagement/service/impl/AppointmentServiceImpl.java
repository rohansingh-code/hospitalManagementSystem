package com.example.springboot.hospitalManagement.service.impl;

import com.example.springboot.hospitalManagement.Entity.Appointment;
import com.example.springboot.hospitalManagement.Entity.Doctor;
import com.example.springboot.hospitalManagement.Entity.Patient;
import com.example.springboot.hospitalManagement.Repository.AppointmentRepository;
import com.example.springboot.hospitalManagement.Repository.DoctorRepository;
import com.example.springboot.hospitalManagement.Repository.PatientRepository;
import com.example.springboot.hospitalManagement.dto.AppointmentResponseDto;
import com.example.springboot.hospitalManagement.dto.CreateAppointmentRequestDto;
import com.example.springboot.hospitalManagement.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto dto, Long patientId) {

        log.info("Creating appointment for doctorId={} and patientId={}",
                dto.getDoctorId(), patientId);

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> {
                    log.error("Doctor not found with id={}", dto.getDoctorId());
                    return new EntityNotFoundException("Doctor not found");
                });

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> {
                    log.error("Patient not found with id={}", patientId);
                    return new EntityNotFoundException("Patient not found");
                });

        Appointment appointment = Appointment.builder()
                .reason(dto.getReason())
                .appointmentTime(dto.getAppointmentTime())
                .build();

        // Set relationships
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        // Maintain bidirectional consistency
        doctor.getAppointments().add(appointment);
        patient.getAppointments().add(appointment);

        appointment = appointmentRepository.save(appointment);

        log.info("Appointment created successfully with id={}", appointment.getId());

        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }
    @Transactional
    @Override
    public Appointment reAssignAppointmentToAnotherDr(Long appointmentId, Long doctorId) {

        log.info("Reassigning appointmentId={} to doctorId={}", appointmentId, doctorId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {
                    log.error("Appointment not found with id={}", appointmentId);
                    return new EntityNotFoundException("Appointment not found");
                });

        Doctor newDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> {
                    log.error("Doctor not found with id={}", doctorId);
                    return new EntityNotFoundException("Doctor not found");
                });

        // Remove from old doctor (important fix)
        Doctor oldDoctor = appointment.getDoctor();
        if (oldDoctor != null) {
            oldDoctor.getAppointments().remove(appointment);
            log.info("Removed appointment {} from old doctor {}", appointmentId, oldDoctor.getId());
        }

        // Assign new doctor
        appointment.setDoctor(newDoctor);
        newDoctor.getAppointments().add(appointment);

        appointment = appointmentRepository.save(appointment);

        log.info("Appointment {} successfully reassigned to doctor {}", appointmentId, doctorId);

        return appointment;
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {

        log.info("Fetching all appointments for doctorId={}", doctorId);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> {
                    log.error("Doctor not found with id={}", doctorId);
                    return new EntityNotFoundException("Doctor not found");
                });

        List<AppointmentResponseDto> appointments = doctor.getAppointments()
                .stream()
                .map(app -> modelMapper.map(app, AppointmentResponseDto.class))
                .toList();

        log.info("Found {} appointments for doctorId={}", appointments.size(), doctorId);

        return appointments;
    }
}