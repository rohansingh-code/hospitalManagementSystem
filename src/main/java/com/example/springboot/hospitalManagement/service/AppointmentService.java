package com.example.springboot.hospitalManagement.service;

import com.example.springboot.hospitalManagement.Entity.Appointment;
import com.example.springboot.hospitalManagement.dto.AppointmentResponseDto;
import com.example.springboot.hospitalManagement.dto.CreateAppointmentRequestDto;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto dto,Long patientId);

    Appointment reAssignAppointmentToAnotherDr(Long appointmentId, Long doctorId);

    List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId);
}
