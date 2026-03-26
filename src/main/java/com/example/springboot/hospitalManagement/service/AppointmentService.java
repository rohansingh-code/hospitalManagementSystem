package com.example.springboot.hospitalManagement.service;

import com.example.springboot.hospitalManagement.Entity.Appointment;

public interface AppointmentService {

    Appointment reAssignAppointmentToAnotherDr(Long appointmentId, Long doctorId);

    Appointment createNewAppointment(Appointment appointment, Long doctorId, Long patientId);


}
