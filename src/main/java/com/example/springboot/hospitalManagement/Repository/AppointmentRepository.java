package com.example.springboot.hospitalManagement.Repository;

import com.example.springboot.hospitalManagement.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}