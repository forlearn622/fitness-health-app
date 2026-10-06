package com.example.fitnessapp.dto.request;

import com.example.fitnessapp.enums.GoalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class GoalRequest {

    @NotBlank(message = "Goal title is required")
    private String title;

    @NotNull(message = "Goal type is required")
    private GoalType goalType;

    @NotNull(message = "Start value is required")
    private Double startValue;

    @NotNull(message = "Target value is required")
    private Double targetValue;

    private Double currentValue;
    private LocalDate targetDate;

    public GoalRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public GoalType getGoalType() { return goalType; }
    public void setGoalType(GoalType goalType) { this.goalType = goalType; }

    public Double getStartValue() { return startValue; }
    public void setStartValue(Double startValue) { this.startValue = startValue; }

    public Double getTargetValue() { return targetValue; }
    public void setTargetValue(Double targetValue) { this.targetValue = targetValue; }

    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
}
