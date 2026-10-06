package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.request.WaterLogRequest;
import com.example.fitnessapp.dto.response.ApiResponse;
import com.example.fitnessapp.dto.response.WaterSummaryDto;
import com.example.fitnessapp.security.UserPrincipal;
import com.example.fitnessapp.service.WaterService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/water")
public class WaterController {

    private final WaterService waterService;

    public WaterController(WaterService waterService) {
        this.waterService = waterService;
    }

    @PostMapping("/log")
    public ResponseEntity<ApiResponse<WaterSummaryDto>> logWater(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody WaterLogRequest request
    ) {
        WaterSummaryDto summary = waterService.logWater(currentUser.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Water logged successfully", summary));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<WaterSummaryDto>> getWaterSummary(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        WaterSummaryDto summary = waterService.getWaterSummary(currentUser.getId(), date);
        return ResponseEntity.ok(ApiResponse.success("Water summary retrieved", summary));
    }
}
