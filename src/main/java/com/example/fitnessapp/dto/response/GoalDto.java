package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.Goal;
import com.example.fitnessapp.enums.GoalStatus;
import com.example.fitnessapp.enums.GoalType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GoalDto {
    private Long id;
    private String title;
    private GoalType goalType;
    private String goalTypeDisplay;
    private Double startValue;
    private Double targetValue;
    private Double currentValue;
    private double progressPercentage;
    private GoalStatus status;
    private LocalDate targetDate;
    private LocalDateTime createdAt;

    public GoalDto() {}

    public GoalDto(Goal g) {
        if (g != null) {
            this.id = g.getId();
            this.title = g.getTitle();
            this.goalType = g.getGoalType();
            this.goalTypeDisplay = g.getGoalType() != null ? g.getGoalType().getDescription() : "";
            this.startValue = g.getStartValue();
            this.targetValue = g.getTargetValue();
            this.currentValue = g.getCurrentValue();
            this.progressPercentage = g.getProgressPercentage();
            this.status = g.getStatus();
            this.targetDate = g.getTargetDate();
            this.createdAt = g.getCreatedAt();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public GoalType getGoalType() { return goalType; }
    public void setGoalType(GoalType goalType) { this.goalType = goalType; }

    public String getGoalTypeDisplay() { return goalTypeDisplay; }
    public void setGoalTypeDisplay(String goalTypeDisplay) { this.goalTypeDisplay = goalTypeDisplay; }

    public Double getStartValue() { return startValue; }
    public void setStartValue(Double startValue) { this.startValue = startValue; }

    public Double getTargetValue() { return targetValue; }
    public void setTargetValue(Double targetValue) { this.targetValue = targetValue; }

    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }

    public double getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(double progressPercentage) { this.progressPercentage = progressPercentage; }

    public GoalStatus getStatus() { return status; }
    public void setStatus(GoalStatus status) { this.status = status; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
