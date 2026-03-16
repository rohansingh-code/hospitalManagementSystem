package com.example.springboot.hospitalManagement.Entity;

import com.example.springboot.hospitalManagement.Entity.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private String email;

    private String gender;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;
}
