package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.request.FoodCreateRequest;
import com.example.fitnessapp.dto.response.FoodDto;
import com.example.fitnessapp.entity.Food;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.repository.FoodRepository;
import com.example.fitnessapp.service.FoodService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodDto> getAllFoods(String category, String query) {
        List<Food> list;
        if (StringUtils.hasText(query)) {
            list = foodRepository.findByNameContainingIgnoreCase(query.trim());
        } else if (StringUtils.hasText(category)) {
            list = foodRepository.findByCategory(category.trim());
        } else {
            list = foodRepository.findAll();
        }
        return list.stream().map(FoodDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodDto> getIndianFoods() {
        return foodRepository.findByIsIndianFoodTrue().stream()
                .map(FoodDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FoodDto addCustomFood(Long userId, FoodCreateRequest request) {
        Food food = new Food();
        food.setName(request.getName().trim());
        food.setCategory(request.getCategory().trim());
        food.setServingSize(request.getServingSize());
        food.setServingUnit(request.getServingUnit().trim());
        food.setCalories(request.getCalories());
        food.setProtein(request.getProtein());
        food.setCarbs(request.getCarbs());
        food.setFat(request.getFat());
        food.setFiber(request.getFiber() != null ? request.getFiber() : 0.0);
        food.setIndianFood(request.isIndianFood());
        food.setCustom(true);

        Food saved = foodRepository.save(food);
        return new FoodDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public FoodDto getFoodById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "id", id));
        return new FoodDto(food);
    }
}
