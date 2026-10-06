package com.example.fitnessapp.dto.request;

import com.example.fitnessapp.enums.GoalStatus;

public class GoalUpdateRequest {
    private Double currentValue;
    private GoalStatus status;

    public GoalUpdateRequest() {}

    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }

    public GoalStatus getStatus() { return status; }
    public void setStatus(GoalStatus status) { this.status = status; }
}
