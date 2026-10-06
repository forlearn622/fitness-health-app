package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.request.FoodLogRequest;
import com.example.fitnessapp.dto.response.DailyDietSummaryDto;
import com.example.fitnessapp.dto.response.DietDayDto;
import com.example.fitnessapp.dto.response.DietPlanDto;
import com.example.fitnessapp.dto.response.FoodLogDto;

import java.time.LocalDate;

public interface DietService {
    DietPlanDto generatePersonalizedDietPlan(Long userId);
    DietPlanDto getActiveDietPlan(Long userId);
    DietDayDto getTodayDietPlan(Long userId);
    FoodLogDto logFood(Long userId, FoodLogRequest request);
    void deleteFoodLog(Long userId, Long foodLogId);
    DailyDietSummaryDto getDailyDietSummary(Long userId, LocalDate date);
}
