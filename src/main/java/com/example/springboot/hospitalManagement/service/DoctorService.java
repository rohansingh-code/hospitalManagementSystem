package com.example.springboot.hospitalManagement.service;

import com.example.springboot.hospitalManagement.dto.DoctorResponseDto;

import java.util.List;


public interface DoctorService {
    List<DoctorResponseDto> getAllDoctors();
}
