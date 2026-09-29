package com.example.fitnessapp.dto.request;

import com.example.fitnessapp.enums.WorkoutStatus;
import jakarta.validation.constraints.NotNull;

public class ExerciseStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private WorkoutStatus status;

    private Integer setsCompleted;
    private Integer repsCompleted;
    private Double weightUsedKg;

    public ExerciseStatusUpdateRequest() {}

    public WorkoutStatus getStatus() { return status; }
    public void setStatus(WorkoutStatus status) { this.status = status; }

    public Integer getSetsCompleted() { return setsCompleted; }
    public void setSetsCompleted(Integer setsCompleted) { this.setsCompleted = setsCompleted; }

    public Integer getRepsCompleted() { return repsCompleted; }
    public void setRepsCompleted(Integer repsCompleted) { this.repsCompleted = repsCompleted; }

    public Double getWeightUsedKg() { return weightUsedKg; }
    public void setWeightUsedKg(Double weightUsedKg) { this.weightUsedKg = weightUsedKg; }
}
