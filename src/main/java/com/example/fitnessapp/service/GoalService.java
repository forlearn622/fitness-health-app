package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.request.GoalRequest;
import com.example.fitnessapp.dto.request.GoalUpdateRequest;
import com.example.fitnessapp.dto.response.GoalDto;
import com.example.fitnessapp.enums.GoalStatus;

import java.util.List;

public interface GoalService {
    GoalDto createGoal(Long userId, GoalRequest request);
    List<GoalDto> getUserGoals(Long userId, GoalStatus status);
    GoalDto getGoalById(Long userId, Long goalId);
    GoalDto updateGoal(Long userId, Long goalId, GoalUpdateRequest request);
    void deleteGoal(Long userId, Long goalId);
}
