package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.request.DailyProgressRequest;
import com.example.fitnessapp.dto.response.DailyProgressDto;
import com.example.fitnessapp.dto.response.DashboardOverviewDto;

import java.time.LocalDate;
import java.util.List;

public interface ProgressService {
    DailyProgressDto logProgress(Long userId, DailyProgressRequest request);
    DailyProgressDto getTodayProgress(Long userId);
    DailyProgressDto getProgressByDate(Long userId, LocalDate date);
    List<DailyProgressDto> getProgressHistory(Long userId, int days);
    DashboardOverviewDto getDashboardOverview(Long userId);
}
