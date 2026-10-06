package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.DailyProgress;

import java.time.LocalDate;

public class DailyProgressDto {
    private Long id;
    private LocalDate logDate;
    private Double weightKg;
    private Double caloriesConsumed;
    private Double caloriesBurned;
    private Integer waterConsumedMl;
    private Double sleepHours;
    private Integer stepsCount;
    private boolean workoutCompleted;
    private String notes;

    public DailyProgressDto() {}

    public DailyProgressDto(DailyProgress dp) {
        if (dp != null) {
            this.id = dp.getId();
            this.logDate = dp.getLogDate();
            this.weightKg = dp.getWeightKg();
            this.caloriesConsumed = dp.getCaloriesConsumed();
            this.caloriesBurned = dp.getCaloriesBurned();
            this.waterConsumedMl = dp.getWaterConsumedMl();
            this.sleepHours = dp.getSleepHours();
            this.stepsCount = dp.getStepsCount();
            this.workoutCompleted = dp.isWorkoutCompleted();
            this.notes = dp.getNotes();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public Double getCaloriesConsumed() { return caloriesConsumed; }
    public void setCaloriesConsumed(Double caloriesConsumed) { this.caloriesConsumed = caloriesConsumed; }

    public Double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(Double caloriesBurned) { this.caloriesBurned = caloriesBurned; }

    public Integer getWaterConsumedMl() { return waterConsumedMl; }
    public void setWaterConsumedMl(Integer waterConsumedMl) { this.waterConsumedMl = waterConsumedMl; }

    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }

    public Integer getStepsCount() { return stepsCount; }
    public void setStepsCount(Integer stepsCount) { this.stepsCount = stepsCount; }

    public boolean isWorkoutCompleted() { return workoutCompleted; }
    public void setWorkoutCompleted(boolean workoutCompleted) { this.workoutCompleted = workoutCompleted; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
