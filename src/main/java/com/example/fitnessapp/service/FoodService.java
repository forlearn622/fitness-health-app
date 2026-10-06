package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.request.FoodCreateRequest;
import com.example.fitnessapp.dto.response.FoodDto;

import java.util.List;

public interface FoodService {
    List<FoodDto> getAllFoods(String category, String query);
    List<FoodDto> getIndianFoods();
    FoodDto addCustomFood(Long userId, FoodCreateRequest request);
    FoodDto getFoodById(Long id);
}
