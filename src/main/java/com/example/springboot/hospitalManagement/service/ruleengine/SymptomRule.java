package com.example.springboot.hospitalManagement.service.ruleengine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class SymptomRule {
    private final String ruleId;
    private final List<String> requiredSymptoms;
    private final String urgency;
    private final String specialty;
    private final String description;
}
