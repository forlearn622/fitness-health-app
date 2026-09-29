package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.DietDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DietDayRepository extends JpaRepository<DietDay, Long> {
    List<DietDay> findByDietPlanIdOrderByDayOfWeekAsc(Long dietPlanId);
    Optional<DietDay> findByDietPlanIdAndDayOfWeek(Long dietPlanId, Integer dayOfWeek);
}
