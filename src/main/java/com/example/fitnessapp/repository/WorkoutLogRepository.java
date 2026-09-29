package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.WorkoutLog;
import com.example.fitnessapp.enums.WorkoutStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    List<WorkoutLog> findByUserIdOrderByLogDateDesc(Long userId);
    List<WorkoutLog> findByUserIdAndLogDateBetweenOrderByLogDateDesc(Long userId, LocalDate startDate, LocalDate endDate);
    Optional<WorkoutLog> findByUserIdAndLogDate(Long userId, LocalDate logDate);
    long countByUserIdAndStatus(Long userId, WorkoutStatus status);

    @Query("SELECT COUNT(DISTINCT w.logDate) FROM WorkoutLog w WHERE w.user.id = :userId AND w.status = :status")
    long countDistinctCompletedWorkoutDays(@Param("userId") Long userId, @Param("status") WorkoutStatus status);
}
