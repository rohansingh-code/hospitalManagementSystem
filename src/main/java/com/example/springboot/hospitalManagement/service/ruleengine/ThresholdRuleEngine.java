package com.example.springboot.hospitalManagement.service.ruleengine;

import com.example.springboot.hospitalManagement.dto.ThresholdAnalysisResult;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThresholdRuleEngine {

    private static final List<SymptomRule> RULES = List.of(
        // CRITICAL
        new SymptomRule("R001", List.of("Chest Pain", "Shortness of Breath"), "critical", "Cardiology", "Possible cardiac emergency \u2014 chest pain with breathing difficulty"),
        new SymptomRule("R002", List.of("Chest Pain", "Dizziness", "Fatigue"), "critical", "Cardiology", "Possible cardiac event \u2014 chest pain with systemic symptoms"),
        new SymptomRule("R003", List.of("Headache", "Blurred Vision", "Numbness"), "critical", "Neurology", "Possible stroke or TIA \u2014 neurological symptom cluster"),
        new SymptomRule("R004", List.of("Fever", "Headache", "Nausea"), "critical", "Neurology", "Possible meningitis \u2014 fever with neurological symptoms"),
        
        // HIGH
        new SymptomRule("R005", List.of("Shortness of Breath", "Dizziness", "Fatigue"), "high", "Pulmonology", "Possible respiratory distress"),
        new SymptomRule("R006", List.of("Abdominal Pain", "Fever", "Nausea"), "high", "Gastroenterology", "Possible acute abdomen \u2014 pain with infection signs"),
        new SymptomRule("R007", List.of("Joint Pain", "Rash", "Fever"), "high", "Rheumatology", "Possible autoimmune or septic arthritis"),
        new SymptomRule("R008", List.of("Dizziness", "Numbness", "Blurred Vision"), "high", "Neurology", "Possible TIA/stroke precursor")
    );

    public ThresholdAnalysisResult analyze(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return ThresholdAnalysisResult.builder().urgency("low").triggeredRule(null).recommendedSpecialty("General Medicine").build();
        }
        
        List<String> normalized = tags.stream().map(String::toLowerCase).collect(Collectors.toList());

        for (SymptomRule rule : RULES) {
            boolean allMatch = rule.getRequiredSymptoms().stream()
                    .allMatch(s -> normalized.contains(s.toLowerCase()));
            if (allMatch) {
                return ThresholdAnalysisResult.builder()
                        .urgency(rule.getUrgency())
                        .triggeredRule(rule.getDescription())
                        .recommendedSpecialty(rule.getSpecialty())
                        .build();
            }
        }

        if (tags.size() >= 5) {
            return ThresholdAnalysisResult.builder().urgency("medium").triggeredRule("5+ symptoms selected").recommendedSpecialty("General Medicine").build();
        }

        return ThresholdAnalysisResult.builder().urgency("low").triggeredRule(null).recommendedSpecialty("General Medicine").build();
    }
}
