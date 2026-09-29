package com.example.fitnessapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diet_days")
public class DietDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_plan_id", nullable = false)
    private DietPlan dietPlan;

    @Column(nullable = false)
    private Integer dayOfWeek; // 1 = Monday, ..., 7 = Sunday

    @OneToMany(mappedBy = "dietDay", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    public DietDay() {}

    public DietDay(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
        meal.setDietDay(this);
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
        meal.setDietDay(null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DietPlan getDietPlan() { return dietPlan; }
    public void setDietPlan(DietPlan dietPlan) { this.dietPlan = dietPlan; }

    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public List<Meal> getMeals() { return meals; }
    public void setMeals(List<Meal> meals) { this.meals = meals; }
}
