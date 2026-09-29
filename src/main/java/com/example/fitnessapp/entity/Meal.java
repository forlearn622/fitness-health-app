package com.example.fitnessapp.entity;

import com.example.fitnessapp.enums.MealType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_day_id", nullable = false)
    private DietDay dietDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MealType mealType;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealFood> mealFoods = new ArrayList<>();

    private Double totalCalories = 0.0;
    private Double totalProtein = 0.0;
    private Double totalCarbs = 0.0;
    private Double totalFat = 0.0;

    public Meal() {}

    public Meal(MealType mealType) {
        this.mealType = mealType;
    }

    public void addMealFood(MealFood mf) {
        mealFoods.add(mf);
        mf.setMeal(this);
    }

    public void removeMealFood(MealFood mf) {
        mealFoods.remove(mf);
        mf.setMeal(null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DietDay getDietDay() { return dietDay; }
    public void setDietDay(DietDay dietDay) { this.dietDay = dietDay; }

    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }

    public List<MealFood> getMealFoods() { return mealFoods; }
    public void setMealFoods(List<MealFood> mealFoods) { this.mealFoods = mealFoods; }

    public Double getTotalCalories() { return totalCalories; }
    public void setTotalCalories(Double totalCalories) { this.totalCalories = totalCalories; }

    public Double getTotalProtein() { return totalProtein; }
    public void setTotalProtein(Double totalProtein) { this.totalProtein = totalProtein; }

    public Double getTotalCarbs() { return totalCarbs; }
    public void setTotalCarbs(Double totalCarbs) { this.totalCarbs = totalCarbs; }

    public Double getTotalFat() { return totalFat; }
    public void setTotalFat(Double totalFat) { this.totalFat = totalFat; }
}
