package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.MealFood;

public class MealFoodDto {
    private Long id;
    private Long foodId;
    private String foodName;
    private Double quantity;
    private String unit;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;

    public MealFoodDto() {}

    public MealFoodDto(MealFood mf) {
        if (mf != null) {
            this.id = mf.getId();
            this.quantity = mf.getQuantity();
            this.unit = mf.getUnit();
            this.calories = mf.getCalories();
            this.protein = mf.getProtein();
            this.carbs = mf.getCarbs();
            this.fat = mf.getFat();
            if (mf.getFood() != null) {
                this.foodId = mf.getFood().getId();
                this.foodName = mf.getFood().getName();
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFoodId() { return foodId; }
    public void setFoodId(Long foodId) { this.foodId = foodId; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

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
}
