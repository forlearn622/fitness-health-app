package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.request.FoodLogRequest;
import com.example.fitnessapp.dto.response.*;
import com.example.fitnessapp.security.UserPrincipal;
import com.example.fitnessapp.service.DietService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/diet")
public class DietController {

    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    @GetMapping("/plan")
    public ResponseEntity<ApiResponse<DietPlanDto>> getActiveDietPlan(@AuthenticationPrincipal UserPrincipal currentUser) {
        DietPlanDto plan = dietService.getActiveDietPlan(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("7-Day personalized diet plan retrieved", plan));
    }

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<DietPlanDto>> generateDietPlan(@AuthenticationPrincipal UserPrincipal currentUser) {
        DietPlanDto plan = dietService.generatePersonalizedDietPlan(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Personalized diet plan generated successfully", plan));
    }

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<DietDayDto>> getTodayDietPlan(@AuthenticationPrincipal UserPrincipal currentUser) {
        DietDayDto todayDiet = dietService.getTodayDietPlan(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Today's meal plan retrieved", todayDiet));
    }

    @PostMapping("/log")
    public ResponseEntity<ApiResponse<FoodLogDto>> logFood(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody FoodLogRequest request
    ) {
        FoodLogDto foodLog = dietService.logFood(currentUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Meal logged successfully", foodLog));
    }

    @DeleteMapping("/log/{foodLogId}")
    public ResponseEntity<ApiResponse<Void>> deleteFoodLog(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long foodLogId
    ) {
        dietService.deleteFoodLog(currentUser.getId(), foodLogId);
        return ResponseEntity.ok(ApiResponse.success("Food log deleted", null));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DailyDietSummaryDto>> getDailyDietSummary(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        DailyDietSummaryDto summary = dietService.getDailyDietSummary(currentUser.getId(), date);
        return ResponseEntity.ok(ApiResponse.success("Daily nutritional intake summary retrieved", summary));
    }
}
