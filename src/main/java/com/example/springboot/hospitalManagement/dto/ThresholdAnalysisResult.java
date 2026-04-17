package com.example.springboot.hospitalManagement.dto;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ThresholdAnalysisResult {
    private String urgency;
    private String triggeredRule;
    private String recommendedSpecialty;
}
