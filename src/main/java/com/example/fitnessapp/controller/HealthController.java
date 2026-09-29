package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("application", "Fitness & Health Management Web Platform");
        healthInfo.put("version", "1.0.0");
        healthInfo.put("phase", "Phase 1: Project Setup Verified");

        return ResponseEntity.ok(ApiResponse.success("Fitness backend is up and running successfully!", healthInfo));
    }
}
