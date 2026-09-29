package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.WaterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WaterLogRepository extends JpaRepository<WaterLog, Long> {
    List<WaterLog> findByUserIdAndLogDateOrderByLoggedAtDesc(Long userId, LocalDate logDate);

    @Query("SELECT COALESCE(SUM(w.amountMl), 0) FROM WaterLog w WHERE w.user.id = :userId AND w.logDate = :logDate")
    Integer sumAmountByUserIdAndLogDate(@Param("userId") Long userId, @Param("logDate") LocalDate logDate);
}
