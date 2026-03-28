package com.example.springboot.hospitalManagement.controller;

import com.example.springboot.hospitalManagement.dto.DoctorResponseDto;
import com.example.springboot.hospitalManagement.service.impl.DoctorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class HospitalController {

    private final DoctorServiceImpl doctorService;

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
}
