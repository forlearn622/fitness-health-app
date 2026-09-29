package com.example.fitnessapp.enums;

public enum MealType {
    BREAKFAST("Breakfast"),
    MORNING_SNACK("Morning Snack"),
    LUNCH("Lunch"),
    EVENING_SNACK("Evening Snack"),
    DINNER("Dinner");

    private final String displayName;

    MealType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
