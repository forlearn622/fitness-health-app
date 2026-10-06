package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.request.FoodCreateRequest;
import com.example.fitnessapp.dto.response.ApiResponse;
import com.example.fitnessapp.dto.response.FoodDto;
import com.example.fitnessapp.security.UserPrincipal;
import com.example.fitnessapp.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FoodDto>>> getAllFoods(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String query
    ) {
        List<FoodDto> foods = foodService.getAllFoods(category, query);
        return ResponseEntity.ok(ApiResponse.success("Food items retrieved", foods));
    }

    @GetMapping("/indian")
    public ResponseEntity<ApiResponse<List<FoodDto>>> getIndianFoods() {
        List<FoodDto> foods = foodService.getIndianFoods();
        return ResponseEntity.ok(ApiResponse.success("Indian food database retrieved", foods));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FoodDto>> addCustomFood(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody FoodCreateRequest request
    ) {
        FoodDto food = foodService.addCustomFood(currentUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Custom food added successfully", food));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FoodDto>> getFoodById(@PathVariable Long id) {
        FoodDto food = foodService.getFoodById(id);
        return ResponseEntity.ok(ApiResponse.success("Food item retrieved", food));
    }
}
