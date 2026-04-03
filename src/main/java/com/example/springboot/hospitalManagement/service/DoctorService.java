package com.example.springboot.hospitalManagement.service;

import com.example.springboot.hospitalManagement.dto.DoctorResponseDto;
import com.example.springboot.hospitalManagement.dto.OnBoardDoctorRequestDto;
import jakarta.transaction.Transactional;

import java.util.List;


public interface DoctorService {

    List<DoctorResponseDto> getAllDoctors();

    DoctorResponseDto onBoardNewDoctor(OnBoardDoctorRequestDto onBoardDoctorRequestDto);
}
