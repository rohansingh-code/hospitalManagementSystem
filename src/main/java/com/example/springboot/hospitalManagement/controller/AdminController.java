package com.example.springboot.hospitalManagement.controller;

import com.example.springboot.hospitalManagement.dto.DoctorResponseDto;
import com.example.springboot.hospitalManagement.dto.OnBoardDoctorRequestDto;
import com.example.springboot.hospitalManagement.dto.PatientResponseDto;
import com.example.springboot.hospitalManagement.service.impl.DoctorServiceImpl;
import com.example.springboot.hospitalManagement.service.impl.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientServiceImpl patientService;
    private fina; DoctorServiceImpl doctorService;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page",defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ){
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber,pageSize));
    }


    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(OnBoardDoctorRequestDto onBoardNewDoctorDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardDoctor(onBoardNewDoctorDto));


    }
}
