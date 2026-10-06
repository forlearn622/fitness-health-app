package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.Food;

public class FoodDto {
    private Long id;
    private String name;
    private String category;
    private Double servingSize;
    private String servingUnit;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
    private Double fiber;
    private boolean isIndianFood;
    private boolean isCustom;

    public FoodDto() {}

    public FoodDto(Food f) {
        if (f != null) {
            this.id = f.getId();
            this.name = f.getName();
            this.category = f.getCategory();
            this.servingSize = f.getServingSize();
            this.servingUnit = f.getServingUnit();
            this.calories = f.getCalories();
            this.protein = f.getProtein();
            this.carbs = f.getCarbs();
            this.fat = f.getFat();
            this.fiber = f.getFiber();
            this.isIndianFood = f.isIndianFood();
            this.isCustom = f.isCustom();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public boolean isCustom() { return isCustom; }
    public void setCustom(boolean custom) { isCustom = custom; }
}
