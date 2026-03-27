package com.example.springboot.hospitalManagement.controller;


import com.example.springboot.hospitalManagement.dto.AppointmentResponseDto;
import com.example.springboot.hospitalManagement.service.impl.AppointmentServiceImpl;
import com.example.springboot.hospitalManagement.service.impl.DoctorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final AppointmentServiceImpl appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctors(){
        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(2L));
    }

}
