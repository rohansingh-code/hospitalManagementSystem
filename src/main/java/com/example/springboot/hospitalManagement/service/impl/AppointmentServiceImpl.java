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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto dto) {

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Appointment appointment = Appointment.builder()
                .reason(dto.getReason())
                .appointmentTime(dto.getAppointmentTime())
                .build();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        patient.getAppointments().add(appointment);

        appointment = appointmentRepository.save(appointment);

        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @Transactional
    @Override
    public Appointment reAssignAppointmentToAnotherDr(Long appointmentId, Long doctorId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        appointment.setDoctor(doctor);

        doctor.getAppointments().add(appointment);

        return appointment;
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        return doctor.getAppointments()
                .stream()
                .map(app -> modelMapper.map(app, AppointmentResponseDto.class))
                .toList();
    }
}