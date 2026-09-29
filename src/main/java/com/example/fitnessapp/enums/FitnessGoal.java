package com.example.fitnessapp.enums;

public enum FitnessGoal {
    MUSCLE_GAIN("Muscle Gain / Bulk", 300, 2.0),
    FAT_LOSS("Fat Loss", -400, 2.2),
    WEIGHT_LOSS("Weight Loss", -500, 1.8),
    MAINTENANCE("Weight Maintenance", 0, 1.6),
    GENERAL_FITNESS("General Fitness", 0, 1.4);

    private final String displayName;
    private final int calorieAdjustment;
    private final double proteinPerKg;

    FitnessGoal(String displayName, int calorieAdjustment, double proteinPerKg) {
        this.displayName = displayName;
        this.calorieAdjustment = calorieAdjustment;
        this.proteinPerKg = proteinPerKg;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCalorieAdjustment() {
        return calorieAdjustment;
    }

    public double getProteinPerKg() {
        return proteinPerKg;
    }
}
