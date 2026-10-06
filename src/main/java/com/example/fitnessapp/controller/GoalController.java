package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.request.GoalRequest;
import com.example.fitnessapp.dto.request.GoalUpdateRequest;
import com.example.fitnessapp.dto.response.ApiResponse;
import com.example.fitnessapp.dto.response.GoalDto;
import com.example.fitnessapp.enums.GoalStatus;
import com.example.fitnessapp.security.UserPrincipal;
import com.example.fitnessapp.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GoalDto>>> getUserGoals(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(required = false) GoalStatus status
    ) {
        List<GoalDto> goals = goalService.getUserGoals(currentUser.getId(), status);
        return ResponseEntity.ok(ApiResponse.success("Goals retrieved successfully", goals));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GoalDto>> getGoalById(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id
    ) {
        GoalDto goal = goalService.getGoalById(currentUser.getId(), id);
        return ResponseEntity.ok(ApiResponse.success("Goal retrieved successfully", goal));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GoalDto>> createGoal(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody GoalRequest request
    ) {
        GoalDto created = goalService.createGoal(currentUser.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Goal created successfully", created));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<GoalDto>> updateGoal(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id,
            @RequestBody GoalUpdateRequest request
    ) {
        GoalDto updated = goalService.updateGoal(currentUser.getId(), id, request);
        return ResponseEntity.ok(ApiResponse.success("Goal updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGoal(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id
    ) {
        goalService.deleteGoal(currentUser.getId(), id);
        return ResponseEntity.ok(ApiResponse.success("Goal deleted successfully", null));
    }
}
