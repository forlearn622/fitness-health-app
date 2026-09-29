package com.example.fitnessapp.util;

import com.example.fitnessapp.enums.ActivityLevel;
import com.example.fitnessapp.enums.FitnessGoal;
import com.example.fitnessapp.enums.Gender;

import java.util.HashMap;
import java.util.Map;

public class FitnessCalculator {

    /**
     * Calculate Body Mass Index: weight (kg) / [height (m)]^2
     */
    public static double calculateBmi(double heightCm, double weightKg) {
        if (heightCm <= 0 || weightKg <= 0) return 0.0;
        double heightM = heightCm / 100.0;
        double bmi = weightKg / (heightM * heightM);
        return Math.round(bmi * 10.0) / 10.0;
    }

    /**
     * Determine WHO BMI Category
     */
    public static String determineBmiCategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25.0) return "Normal weight";
        if (bmi < 30.0) return "Overweight";
        return "Obese";
    }

    /**
     * Calculate Basal Metabolic Rate using Mifflin-St Jeor Equation:
     * Men: BMR = 10 * weight (kg) + 6.25 * height (cm) - 5 * age + 5
     * Women: BMR = 10 * weight (kg) + 6.25 * height (cm) - 5 * age - 161
     */
    public static double calculateBmr(Gender gender, double weightKg, double heightCm, int age) {
        double base = (10.0 * weightKg) + (6.25 * heightCm) - (5.0 * age);
        if (gender == Gender.MALE) {
            return Math.round((base + 5.0) * 10.0) / 10.0;
        } else if (gender == Gender.FEMALE) {
            return Math.round((base - 161.0) * 10.0) / 10.0;
        } else {
            return Math.round((base - 78.0) * 10.0) / 10.0;
        }
    }

    /**
     * Calculate Total Daily Energy Expenditure (TDEE): BMR * Activity Multiplier
     */
    public static double calculateTdee(double bmr, ActivityLevel activityLevel) {
        double multiplier = activityLevel != null ? activityLevel.getMultiplier() : 1.2;
        return Math.round(bmr * multiplier);
    }

    /**
     * Determine daily calorie requirement adjusted for fitness goal:
     * Muscle Gain: +300 kcal
     * Fat Loss: -400 kcal
     * Weight Loss: -500 kcal
     * Maintenance / General Fitness: 0 adjustment
     * Minimum floor: 1200 kcal to prevent dangerous crash diets
     */
    public static int calculateDailyCalorieTarget(double tdee, FitnessGoal goal) {
        int adjustment = goal != null ? goal.getCalorieAdjustment() : 0;
        int target = (int) Math.round(tdee + adjustment);
        return Math.max(target, 1200); // Safe minimum calorie limit
    }

    /**
     * Calculate balanced macronutrient split:
     * Protein: goal-based multiplier * weight (4 kcal/g)
     * Fat: 25% of total calories (9 kcal/g)
     * Carbs: Remaining calories (4 kcal/g)
     */
    public static Map<String, Integer> calculateMacros(int dailyCalories, double weightKg, FitnessGoal goal) {
        double proteinMultiplier = goal != null ? goal.getProteinPerKg() : 1.6;
        int proteinGrams = (int) Math.round(weightKg * proteinMultiplier);
        int proteinCalories = proteinGrams * 4;

        // Fat ~ 25% of daily calories
        int fatCalories = (int) Math.round(dailyCalories * 0.25);
        int fatGrams = (int) Math.round(fatCalories / 9.0);

        // Remaining calories go to carbohydrates
        int remainingCalories = dailyCalories - proteinCalories - (fatGrams * 9);
        int carbsGrams = Math.max(0, (int) Math.round(remainingCalories / 4.0));

        Map<String, Integer> macros = new HashMap<>();
        macros.put("protein", proteinGrams);
        macros.put("fat", fatGrams);
        macros.put("carbs", carbsGrams);
        return macros;
    }

    /**
     * Calculate recommended daily water intake in ml:
     * Baseline: 35 ml per kg body weight + activity bonus
     */
    public static int calculateWaterTarget(double weightKg, ActivityLevel activityLevel) {
        double baseWater = weightKg * 35.0;
        int bonus = switch (activityLevel != null ? activityLevel : ActivityLevel.SEDENTARY) {
            case SEDENTARY -> 0;
            case LIGHTLY_ACTIVE -> 250;
            case MODERATELY_ACTIVE -> 500;
            case VERY_ACTIVE -> 750;
        };
        int total = (int) Math.round(baseWater + bonus);
        // Round to nearest 250ml
        return Math.max(2000, ((total + 125) / 250) * 250);
    }

    /**
     * Calculate Estimated Calories Burned using MET (Metabolic Equivalent of Task):
     * Calories = MET * weight (kg) * (duration (min) / 60)
     */
    public static double calculateCaloriesBurned(double metValue, double weightKg, int durationMinutes) {
        if (durationMinutes <= 0 || weightKg <= 0 || metValue <= 0) return 0.0;
        double calories = metValue * weightKg * (durationMinutes / 60.0);
        return Math.round(calories * 10.0) / 10.0;
    }
}
