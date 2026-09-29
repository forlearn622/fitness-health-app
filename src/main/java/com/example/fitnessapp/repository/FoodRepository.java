package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategory(String category);
    List<Food> findByIsIndianFoodTrue();
    List<Food> findByNameContainingIgnoreCase(String name);
}
