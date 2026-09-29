package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.WorkoutLog;
import com.example.fitnessapp.enums.WorkoutStatus;

import java.time.LocalDate;

public class WorkoutLogSummaryDto {
    private Long id;
    private LocalDate logDate;
    private String title;
    private Integer durationMin;
    private Double caloriesBurned;
    private WorkoutStatus status;
    private int totalExercises;
    private int completedExercises;
    private double completionPercentage;

    public WorkoutLogSummaryDto() {}

    public WorkoutLogSummaryDto(WorkoutLog log) {
        if (log != null) {
            this.id = log.getId();
            this.logDate = log.getLogDate();
            this.title = log.getWorkoutTitle();
            this.durationMin = log.getTotalDurationMin();
            this.caloriesBurned = log.getTotalCaloriesBurned();
            this.status = log.getStatus();

            if (log.getExerciseLogs() != null && !log.getExerciseLogs().isEmpty()) {
                this.totalExercises = log.getExerciseLogs().size();
                this.completedExercises = (int) log.getExerciseLogs().stream()
                        .filter(el -> el.getStatus() == WorkoutStatus.COMPLETED)
                        .count();
                this.completionPercentage = Math.round(((double) this.completedExercises / this.totalExercises) * 100.0);
            } else {
                this.completionPercentage = log.getStatus() == WorkoutStatus.COMPLETED ? 100.0 : 0.0;
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getDurationMin() { return durationMin; }
    public void setDurationMin(Integer durationMin) { this.durationMin = durationMin; }

    public Double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(Double caloriesBurned) { this.caloriesBurned = caloriesBurned; }

    public WorkoutStatus getStatus() { return status; }
    public void setStatus(WorkoutStatus status) { this.status = status; }

    public int getTotalExercises() { return totalExercises; }
    public void setTotalExercises(int totalExercises) { this.totalExercises = totalExercises; }

    public int getCompletedExercises() { return completedExercises; }
    public void setCompletedExercises(int completedExercises) { this.completedExercises = completedExercises; }

    public double getCompletionPercentage() { return completionPercentage; }
    public void setCompletionPercentage(double completionPercentage) { this.completionPercentage = completionPercentage; }
}
