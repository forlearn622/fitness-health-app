package com.example.fitnessapp.dto.request;

import com.example.fitnessapp.enums.ActivityLevel;
import com.example.fitnessapp.enums.FitnessExperience;
import com.example.fitnessapp.enums.FitnessGoal;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProfileUpdateRequest {

    @NotNull(message = "Age is required")
    @Min(value = 12, message = "Age must be at least 12")
    @Max(value = 100, message = "Age must be under 100")
    private Integer age;

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be a positive number")
    private Double heightCm;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be a positive number")
    private Double weightKg;

    @Positive(message = "Target weight must be a positive number")
    private Double targetWeightKg;

    @NotNull(message = "Activity level is required")
    private ActivityLevel activityLevel;

    @NotNull(message = "Fitness experience is required")
    private FitnessExperience fitnessExperience;

    @NotNull(message = "Fitness goal is required")
    private FitnessGoal fitnessGoal;

    public ProfileUpdateRequest() {}

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public Double getTargetWeightKg() { return targetWeightKg; }
    public void setTargetWeightKg(Double targetWeightKg) { this.targetWeightKg = targetWeightKg; }

    public ActivityLevel getActivityLevel() { return activityLevel; }
    public void setActivityLevel(ActivityLevel activityLevel) { this.activityLevel = activityLevel; }

    public FitnessExperience getFitnessExperience() { return fitnessExperience; }
    public void setFitnessExperience(FitnessExperience fitnessExperience) { this.fitnessExperience = fitnessExperience; }

    public FitnessGoal getFitnessGoal() { return fitnessGoal; }
    public void setFitnessGoal(FitnessGoal fitnessGoal) { this.fitnessGoal = fitnessGoal; }
}
