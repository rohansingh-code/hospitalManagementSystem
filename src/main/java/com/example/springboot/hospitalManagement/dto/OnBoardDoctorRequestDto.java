package com.example.springboot.hospitalManagement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OnBoardDoctorRequestDto {
    private Long userId;
    private String specialization;
    private String name;
}
