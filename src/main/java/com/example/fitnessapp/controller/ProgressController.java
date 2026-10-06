package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.request.DailyProgressRequest;
import com.example.fitnessapp.dto.response.ApiResponse;
import com.example.fitnessapp.dto.response.DailyProgressDto;
import com.example.fitnessapp.dto.response.DashboardOverviewDto;
import com.example.fitnessapp.security.UserPrincipal;
import com.example.fitnessapp.service.ProgressService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DailyProgressDto>> logDailyProgress(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody DailyProgressRequest request
    ) {
        DailyProgressDto dto = progressService.logProgress(currentUser.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Daily progress saved successfully", dto));
    }

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<DailyProgressDto>> getTodayProgress(
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        DailyProgressDto dto = progressService.getTodayProgress(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Today's progress retrieved", dto));
    }

    @GetMapping("/by-date")
    public ResponseEntity<ApiResponse<DailyProgressDto>> getProgressByDate(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        DailyProgressDto dto = progressService.getProgressByDate(currentUser.getId(), date);
        return ResponseEntity.ok(ApiResponse.success("Progress retrieved", dto));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<DailyProgressDto>>> getProgressHistory(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "30") int days
    ) {
        List<DailyProgressDto> history = progressService.getProgressHistory(currentUser.getId(), days);
        return ResponseEntity.ok(ApiResponse.success("Progress history retrieved", history));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardOverviewDto>> getDashboardOverview(
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        DashboardOverviewDto overview = progressService.getDashboardOverview(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Dashboard overview retrieved", overview));
    }
}
