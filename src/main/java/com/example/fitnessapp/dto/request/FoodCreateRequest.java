package com.example.fitnessapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FoodCreateRequest {

    @NotBlank(message = "Food name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Serving size is required")
    @Positive(message = "Serving size must be positive")
    private Double servingSize;

    @NotBlank(message = "Serving unit is required (e.g. g, ml, bowl, roti, piece)")
    private String servingUnit;

    @NotNull(message = "Calories are required")
    @Positive(message = "Calories must be positive")
    private Double calories;

    @NotNull(message = "Protein is required")
    private Double protein;

    @NotNull(message = "Carbohydrates are required")
    private Double carbs;

    @NotNull(message = "Fat is required")
    private Double fat;

    private Double fiber = 0.0;
    private boolean isIndianFood = true;

    public FoodCreateRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getServingSize() { return servingSize; }
    public void setServingSize(Double servingSize) { this.servingSize = servingSize; }

    public String getServingUnit() { return servingUnit; }
    public void setServingUnit(String servingUnit) { this.servingUnit = servingUnit; }

    public Double getCalories() { return calories; }
    public void setCalories(Double calories) { this.calories = calories; }

    public Double getProtein() { return protein; }
    public void setProtein(Double protein) { this.protein = protein; }

    public Double getCarbs() { return carbs; }
    public void setCarbs(Double carbs) { this.carbs = carbs; }

    public Double getFat() { return fat; }
    public void setFat(Double fat) { this.fat = fat; }

    public Double getFiber() { return fiber; }
    public void setFiber(Double fiber) { this.fiber = fiber; }

    public boolean isIndianFood() { return isIndianFood; }
    public void setIndianFood(boolean indianFood) { isIndianFood = indianFood; }
}
