package com.example.springboot.hospitalManagement.controller;

import com.example.springboot.hospitalManagement.dto.QueryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIProxyController {

    private final RestTemplate restTemplate;
    private final ThresholdRuleEngine thresholdRuleEngine;

    @Value("${ai.service.url:http://host.docker.internal:8081}")
    private String aiServiceUrl;

    @PostMapping("/query")
    public ResponseEntity<?> queryAI(@RequestBody QueryDto dto) {
        String url = aiServiceUrl + "/ai/query";
        
        ThresholdAnalysisResult thresholdResult = null;
        try {
            // 1. Run local Threshold engine first
            thresholdResult = thresholdRuleEngine.analyze(dto.getSelectedTags());
            log.info("Threshold engine analysis completed successfully.");
        } catch (Exception e) {
            log.warn("Threshold engine analysis failed: {}", e.getMessage());
        }

        Object aiResponse;
        try {
            // 2. Run Ai then
            log.info("Calling AI service at {}", url);
            aiResponse = restTemplate.postForObject(url, dto, Object.class);
            log.info("AI service returned successfully.");
        } catch (Exception e) {
            log.error("Error communicating with AI service: {}", e.getMessage(), e);
            aiResponse = Map.of(
                    "error", true,
                    "message", "AI assistant is currently unavailable due to technical difficulties.",
                    "fallbackSystem", "Threshold engine remains continuously active for emergency detection."
            );
        }
        
        // 3. Merge Responses
        Map<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("thresholdAnalysis", thresholdResult);
        finalResponse.put("aiAnalysis", aiResponse);
        
        return ResponseEntity.ok(finalResponse);
    }
    
    @PostMapping("/test-query")
    public ResponseEntity<?> testQuery(@RequestBody Map<String, String> body) {
        String url = aiServiceUrl + "/ai/query";

        try {
            Object response = restTemplate.postForObject(url, body, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Test query to AI service failed: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body("Error calling AI service: " + e.getMessage());
        }
    }
}
