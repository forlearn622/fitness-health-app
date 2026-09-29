package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.Meal;
import com.example.fitnessapp.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByDietDayId(Long dietDayId);
    Optional<Meal> findByDietDayIdAndMealType(Long dietDayId, MealType mealType);
}
