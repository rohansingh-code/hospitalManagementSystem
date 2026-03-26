package com.example.springboot.hospitalManagement.service.impl;

import com.example.springboot.hospitalManagement.Entity.Appointment;
import com.example.springboot.hospitalManagement.Entity.Doctor;
import com.example.springboot.hospitalManagement.Entity.Patient;
import com.example.springboot.hospitalManagement.Repository.AppointmentRepository;
import com.example.springboot.hospitalManagement.Repository.DoctorRepository;
import com.example.springboot.hospitalManagement.Repository.PatientRepository;
import com.example.springboot.hospitalManagement.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    @Override
    public Appointment createNewAppointment(Appointment appointment, Long doctorId, Long patientId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (appointment.getId() != null) {
            throw new IllegalArgumentException("New appointment cannot already have an ID");
        }

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        patient.getAppointments().add(appointment);

        return appointmentRepository.save(appointment);
    }
}
