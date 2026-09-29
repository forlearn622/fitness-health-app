package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.enums.FitnessExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByCategory(String category);
    List<Exercise> findByTargetMuscleGroup(String targetMuscleGroup);
    List<Exercise> findByDifficulty(FitnessExperience difficulty);
    List<Exercise> findByNameContainingIgnoreCase(String name);
}
