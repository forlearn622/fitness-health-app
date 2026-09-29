package com.example.fitnessapp.enums;

public enum GoalType {
    TARGET_WEIGHT("Target Weight (kg)"),
    WORKOUT_STREAK("Workout Streak (days)"),
    TOTAL_WORKOUTS("Total Workouts Completed"),
    DAILY_CALORIES("Daily Calorie Target (kcal)"),
    WATER_INTAKE("Daily Water Intake (ml)");

    private final String description;

    GoalType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
