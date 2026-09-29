package com.example.fitnessapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 50)
    private String category; // Grains, Dairy, Pulses/Dal, Poultry/Meat, Vegetables, Fruits, Snacks

    @Column(nullable = false)
    private Double servingSize = 100.0;

    @Column(nullable = false, length = 30)
    private String servingUnit = "g"; // g, ml, cup, piece, roti, bowl

    @Column(nullable = false)
    private Double calories; // kcal per serving

    @Column(nullable = false)
    private Double protein; // grams

    @Column(nullable = false)
    private Double carbs; // grams

    @Column(nullable = false)
    private Double fat; // grams

    private Double fiber = 0.0; // grams

    @Column(nullable = false)
    private boolean isIndianFood = false;

    private boolean isCustom = false;

    public Food() {}

    public Food(String name, String category, Double servingSize, String servingUnit, Double calories, Double protein, Double carbs, Double fat, Double fiber, boolean isIndianFood) {
        this.name = name;
        this.category = category;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.isIndianFood = isIndianFood;
        this.isCustom = false;
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
