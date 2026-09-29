package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.WorkoutExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutExerciseLogRepository extends JpaRepository<WorkoutExerciseLog, Long> {
    List<WorkoutExerciseLog> findByWorkoutLogId(Long workoutLogId);
}
