package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.WorkoutDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutDayRepository extends JpaRepository<WorkoutDay, Long> {
    List<WorkoutDay> findByWorkoutPlanIdOrderByDayOfWeekAsc(Long workoutPlanId);
    Optional<WorkoutDay> findByWorkoutPlanIdAndDayOfWeek(Long workoutPlanId, Integer dayOfWeek);
}
