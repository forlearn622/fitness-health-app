package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.request.ExerciseStatusUpdateRequest;
import com.example.fitnessapp.dto.response.*;
import com.example.fitnessapp.security.UserPrincipal;
import com.example.fitnessapp.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<TodayWorkoutDto>> getTodayWorkout(@AuthenticationPrincipal UserPrincipal currentUser) {
        TodayWorkoutDto todayWorkout = workoutService.getTodayWorkout(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Today's workout session retrieved", todayWorkout));
    }

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<WorkoutPlanDto>> generateWorkoutPlan(@AuthenticationPrincipal UserPrincipal currentUser) {
        WorkoutPlanDto plan = workoutService.generatePersonalizedWorkoutPlan(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Personalized workout plan generated successfully", plan));
    }

    @GetMapping("/plan")
    public ResponseEntity<ApiResponse<WorkoutPlanDto>> getActiveWorkoutPlan(@AuthenticationPrincipal UserPrincipal currentUser) {
        WorkoutPlanDto plan = workoutService.getActiveWorkoutPlan(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Active workout plan retrieved", plan));
    }

    @PatchMapping("/exercises/{exerciseLogId}")
    public ResponseEntity<ApiResponse<WorkoutExerciseLogDto>> updateExerciseStatus(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long exerciseLogId,
            @Valid @RequestBody ExerciseStatusUpdateRequest request
    ) {
        WorkoutExerciseLogDto updated = workoutService.updateExerciseStatus(currentUser.getId(), exerciseLogId, request);
        return ResponseEntity.ok(ApiResponse.success("Exercise status updated", updated));
    }

    @PostMapping("/today/complete")
    public ResponseEntity<ApiResponse<TodayWorkoutDto>> completeTodayWorkout(@AuthenticationPrincipal UserPrincipal currentUser) {
        TodayWorkoutDto completedSession = workoutService.completeTodayWorkout(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Workout completed! Excellent effort.", completedSession));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<WorkoutLogSummaryDto>>> getWorkoutHistory(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<WorkoutLogSummaryDto> history = workoutService.getWorkoutHistory(currentUser.getId(), filter, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Workout history retrieved", history));
    }

    @GetMapping("/streak")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStreak(@AuthenticationPrincipal UserPrincipal currentUser) {
        long streak = workoutService.getWorkoutStreak(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Workout streak retrieved", Map.of("streakDays", streak)));
    }
}
