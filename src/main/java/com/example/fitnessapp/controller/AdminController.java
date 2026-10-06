package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.response.AdminStatsDto;
import com.example.fitnessapp.dto.response.ApiResponse;
import com.example.fitnessapp.dto.response.UserResponseDto;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.exception.BadRequestException;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.repository.*;
import com.example.fitnessapp.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final WorkoutLogRepository workoutLogRepository;
    private final ExerciseRepository exerciseRepository;
    private final FoodRepository foodRepository;
    private final GoalRepository goalRepository;

    public AdminController(
            UserRepository userRepository,
            WorkoutLogRepository workoutLogRepository,
            ExerciseRepository exerciseRepository,
            FoodRepository foodRepository,
            GoalRepository goalRepository
    ) {
        this.userRepository = userRepository;
        this.workoutLogRepository = workoutLogRepository;
        this.exerciseRepository = exerciseRepository;
        this.foodRepository = foodRepository;
        this.goalRepository = goalRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        List<User> users = userRepository.findAllByOrderByCreatedAtDesc();
        List<UserResponseDto> dtos = users.stream().map(UserResponseDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", dtos));
    }

    @PatchMapping("/users/{id}/toggle")
    public ResponseEntity<ApiResponse<UserResponseDto>> toggleUserStatus(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id
    ) {
        if (currentUser.getId().equals(id)) {
            throw new BadRequestException("You cannot change the active status of your own account.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setActive(!user.isActive());
        User saved = userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success(
                "User status updated to " + (saved.isActive() ? "Active" : "Inactive"),
                new UserResponseDto(saved)
        ));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdminStatsDto>> getAdminStats() {
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countByIsActiveTrue();
        long totalWorkouts = workoutLogRepository.count();
        long totalExercises = exerciseRepository.count();
        long totalFoods = foodRepository.count();
        long totalGoals = goalRepository.count();

        AdminStatsDto stats = new AdminStatsDto(
                totalUsers,
                activeUsers,
                totalWorkouts,
                totalExercises,
                totalFoods,
                totalGoals
        );

        return ResponseEntity.ok(ApiResponse.success("Admin stats retrieved successfully", stats));
    }
}
