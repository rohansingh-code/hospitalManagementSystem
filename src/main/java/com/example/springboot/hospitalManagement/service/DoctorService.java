package com.example.springboot.hospitalManagement.service;

import com.example.springboot.hospitalManagement.dto.DoctorResponseDto;
import com.example.springboot.hospitalManagement.dto.OnBoardDoctorRequestDto;

import java.util.List;


public interface DoctorService {

    List<DoctorResponseDto> getAllDoctors();

    DoctorResponseDto onBoardNewDoctor(OnBoardDoctorRequestDto onBoardDoctorRequestDto);

    List<DoctorResponseDto> getDoctorsBySpecialization(String specialization);
}
