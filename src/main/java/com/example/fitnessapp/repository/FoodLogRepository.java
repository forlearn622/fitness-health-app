package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.FoodLog;
import com.example.fitnessapp.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodLogRepository extends JpaRepository<FoodLog, Long> {
    List<FoodLog> findByUserIdAndLogDateOrderByLoggedAtAsc(Long userId, LocalDate logDate);
    List<FoodLog> findByUserIdAndLogDateAndMealType(Long userId, LocalDate logDate, MealType mealType);

    @Query("SELECT COALESCE(SUM(fl.calories), 0.0) FROM FoodLog fl WHERE fl.user.id = :userId AND fl.logDate = :logDate")
    Double sumCaloriesByUserIdAndDate(@Param("userId") Long userId, @Param("logDate") LocalDate logDate);

    @Query("SELECT COALESCE(SUM(fl.protein), 0.0) FROM FoodLog fl WHERE fl.user.id = :userId AND fl.logDate = :logDate")
    Double sumProteinByUserIdAndDate(@Param("userId") Long userId, @Param("logDate") LocalDate logDate);
}
