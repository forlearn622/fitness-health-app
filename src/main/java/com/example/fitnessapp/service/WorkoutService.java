package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.request.ExerciseStatusUpdateRequest;
import com.example.fitnessapp.dto.response.TodayWorkoutDto;
import com.example.fitnessapp.dto.response.WorkoutExerciseLogDto;
import com.example.fitnessapp.dto.response.WorkoutLogSummaryDto;
import com.example.fitnessapp.dto.response.WorkoutPlanDto;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutService {
    WorkoutPlanDto generatePersonalizedWorkoutPlan(Long userId);
    WorkoutPlanDto getActiveWorkoutPlan(Long userId);
    TodayWorkoutDto getTodayWorkout(Long userId);
    WorkoutExerciseLogDto updateExerciseStatus(Long userId, Long exerciseLogId, ExerciseStatusUpdateRequest request);
    TodayWorkoutDto completeTodayWorkout(Long userId);
    List<WorkoutLogSummaryDto> getWorkoutHistory(Long userId, String filter, LocalDate startDate, LocalDate endDate);
    long getWorkoutStreak(Long userId);
}
