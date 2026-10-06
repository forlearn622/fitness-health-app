package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.request.WaterLogRequest;
import com.example.fitnessapp.dto.response.WaterSummaryDto;

import java.time.LocalDate;

public interface WaterService {
    WaterSummaryDto logWater(Long userId, WaterLogRequest request);
    WaterSummaryDto getWaterSummary(Long userId, LocalDate date);
}
