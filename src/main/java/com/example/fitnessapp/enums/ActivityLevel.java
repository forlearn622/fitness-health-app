package com.example.fitnessapp.enums;

public enum ActivityLevel {
    SEDENTARY(1.2, "Sedentary (Little or no exercise)"),
    LIGHTLY_ACTIVE(1.375, "Lightly Active (Exercise 1-3 days/week)"),
    MODERATELY_ACTIVE(1.55, "Moderately Active (Exercise 3-5 days/week)"),
    VERY_ACTIVE(1.725, "Very Active (Hard exercise 6-7 days/week)");

    private final double multiplier;
    private final String description;

    ActivityLevel(double multiplier, String description) {
        this.multiplier = multiplier;
        this.description = description;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public String getDescription() {
        return description;
    }
}
