package com.example.springboot.hospitalManagement.service;

import com.example.springboot.hospitalManagement.Entity.Appointment;

public interface AppointmentService {

    Appointment createNewAppointment(Appointment appointment,Long doctorId,Long patientId);

}
