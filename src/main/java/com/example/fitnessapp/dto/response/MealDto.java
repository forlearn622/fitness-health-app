package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.Meal;
import com.example.fitnessapp.enums.MealType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealDto {
    private Long id;
    private MealType mealType;
    private String mealTypeDisplay;
    private Double totalCalories;
    private Double totalProtein;
    private Double totalCarbs;
    private Double totalFat;
    private List<MealFoodDto> foods = new ArrayList<>();

    public MealDto() {}

    public MealDto(Meal meal) {
        if (meal != null) {
            this.id = meal.getId();
            this.mealType = meal.getMealType();
            this.mealTypeDisplay = meal.getMealType() != null ? meal.getMealType().getDisplayName() : "";
            this.totalCalories = meal.getTotalCalories();
            this.totalProtein = meal.getTotalProtein();
            this.totalCarbs = meal.getTotalCarbs();
            this.totalFat = meal.getTotalFat();
            if (meal.getMealFoods() != null) {
                this.foods = meal.getMealFoods().stream()
                        .map(MealFoodDto::new)
                        .collect(Collectors.toList());
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }

    public String getMealTypeDisplay() { return mealTypeDisplay; }
    public void setMealTypeDisplay(String mealTypeDisplay) { this.mealTypeDisplay = mealTypeDisplay; }

    public Double getTotalCalories() { return totalCalories; }
    public void setTotalCalories(Double totalCalories) { this.totalCalories = totalCalories; }

    public Double getTotalProtein() { return totalProtein; }
    public void setTotalProtein(Double totalProtein) { this.totalProtein = totalProtein; }

    public Double getTotalCarbs() { return totalCarbs; }
    public void setTotalCarbs(Double totalCarbs) { this.totalCarbs = totalCarbs; }

    public Double getTotalFat() { return totalFat; }
    public void setTotalFat(Double totalFat) { this.totalFat = totalFat; }

    public List<MealFoodDto> getFoods() { return foods; }
    public void setFoods(List<MealFoodDto> foods) { this.foods = foods; }
}
