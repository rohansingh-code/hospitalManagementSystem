package com.example.springboot.hospitalManagement.dto;

import com.example.springboot.hospitalManagement.Entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String username;
    private String password;
    private String name;

    private Set<RoleType> roles = new HashSet<>();
}
