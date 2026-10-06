package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.FoodLog;
import com.example.fitnessapp.enums.MealType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoodLogDto {
    private Long id;
    private Long foodId;
    private String foodName;
    private MealType mealType;
    private String mealTypeDisplay;
    private LocalDate logDate;
    private Double quantity;
    private String unit;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
    private LocalDateTime loggedAt;

    public FoodLogDto() {}

    public FoodLogDto(FoodLog log) {
        if (log != null) {
            this.id = log.getId();
            this.foodName = log.getFoodName();
            this.mealType = log.getMealType();
            this.mealTypeDisplay = log.getMealType() != null ? log.getMealType().getDisplayName() : "";
            this.logDate = log.getLogDate();
            this.quantity = log.getQuantity();
            this.unit = log.getUnit();
            this.calories = log.getCalories();
            this.protein = log.getProtein();
            this.carbs = log.getCarbs();
            this.fat = log.getFat();
            this.loggedAt = log.getLoggedAt();
            if (log.getFood() != null) {
                this.foodId = log.getFood().getId();
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFoodId() { return foodId; }
    public void setFoodId(Long foodId) { this.foodId = foodId; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }

    public String getMealTypeDisplay() { return mealTypeDisplay; }
    public void setMealTypeDisplay(String mealTypeDisplay) { this.mealTypeDisplay = mealTypeDisplay; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Double getCalories() { return calories; }
    public void setCalories(Double calories) { this.calories = calories; }

    public Double getProtein() { return protein; }
    public void setProtein(Double protein) { this.protein = protein; }

    public Double getCarbs() { return carbs; }
    public void setCarbs(Double carbs) { this.carbs = carbs; }

    public Double getFat() { return fat; }
    public void setFat(Double fat) { this.fat = fat; }

    public LocalDateTime getLoggedAt() { return loggedAt; }
    public void setLoggedAt(LocalDateTime loggedAt) { this.loggedAt = loggedAt; }
}
