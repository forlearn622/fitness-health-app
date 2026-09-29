package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.DailyProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyProgressRepository extends JpaRepository<DailyProgress, Long> {
    Optional<DailyProgress> findByUserIdAndLogDate(Long userId, LocalDate logDate);
    List<DailyProgress> findByUserIdAndLogDateBetweenOrderByLogDateAsc(Long userId, LocalDate startDate, LocalDate endDate);
    List<DailyProgress> findByUserIdOrderByLogDateDesc(Long userId);
    Optional<DailyProgress> findTopByUserIdOrderByLogDateDesc(Long userId);
}
