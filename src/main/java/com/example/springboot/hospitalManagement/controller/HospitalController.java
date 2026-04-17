package com.example.springboot.hospitalManagement.controller;

import com.example.springboot.hospitalManagement.dto.DoctorResponseDto;
import com.example.springboot.hospitalManagement.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class HospitalController {

    private final DoctorService doctorService;

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getDoctors(
            @RequestParam(required = false) String specialization
    ){
        if (specialization != null) {
            return ResponseEntity.ok(
                    doctorService.getDoctorsBySpecialization(specialization)
            );
        }
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
}