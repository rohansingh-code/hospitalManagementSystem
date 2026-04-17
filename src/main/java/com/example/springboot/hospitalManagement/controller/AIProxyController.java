package com.example.springboot.hospitalManagement.controller;

import com.example.springboot.hospitalManagement.dto.QueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.springboot.hospitalManagement.dto.ThresholdAnalysisResult;
import com.example.springboot.hospitalManagement.service.ruleengine.ThresholdRuleEngine;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIProxyController {

    private final RestTemplate restTemplate;
    private final ThresholdRuleEngine thresholdRuleEngine;

    @Value("${ai.service.url:http://localhost:8000}")
    private String aiServiceUrl;

    @PostMapping("/query")
    public ResponseEntity<?> queryAI(@RequestBody QueryDto dto) {
        String url = aiServiceUrl + "/agent";
        try {
            // 1. Run local Threshold engine first
            ThresholdAnalysisResult thresholdResult = thresholdRuleEngine.analyze(dto.getSelectedTags());
            
            // 2. Run Ai then
            Object aiResponse = restTemplate.postForObject(url, dto, Object.class);
            
            // 3. Merge Responses
            Map<String, Object> finalResponse = new HashMap<>();
            finalResponse.put("thresholdAnalysis", thresholdResult);
            finalResponse.put("aiAnalysis", aiResponse);
            
            return ResponseEntity.ok(finalResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error communicating with AI service: " + e.getMessage());
        }
    }
}
