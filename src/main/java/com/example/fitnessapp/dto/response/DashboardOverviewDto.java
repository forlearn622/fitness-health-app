package com.example.fitnessapp.dto.response;

import java.util.ArrayList;
import java.util.List;

public class DashboardOverviewDto {
    private String fullName;
    private Double currentWeightKg;
    private Double targetWeightKg;
    private Double bmi;
    private String bmiCategory;
    private String fitnessGoalDisplay;
    private Integer dailyCalorieTarget;
    private Integer proteinTargetG;
    private Integer waterTargetMl;

    // Today's Progress
    private boolean workoutCompleted = false;
    private double workoutCompletionPercentage = 0.0;
    private String todayWorkoutTitle;
    private Double caloriesBurned = 0.0;
    private Double caloriesConsumed = 0.0;
    private Double remainingCalories = 0.0;
    private Integer waterConsumedMl = 0;
    private double waterPercentage = 0.0;
    private Double sleepHours = 0.0;
    private Integer stepsCount = 0;

    // Consistency
    private long currentStreakDays = 0;

    // Weekly summary
    private int weeklyWorkoutsCompleted = 0;
    private double weeklyCaloriesBurned = 0.0;
    private double weeklyAvgCaloriesConsumed = 0.0;
    private double weightChangeKg = 0.0;

    // Historical points for Chart.js
    private List<DailyProgressDto> recentProgress = new ArrayList<>();

    public DashboardOverviewDto() {}

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Double getCurrentWeightKg() { return currentWeightKg; }
    public void setCurrentWeightKg(Double currentWeightKg) { this.currentWeightKg = currentWeightKg; }

    public Double getTargetWeightKg() { return targetWeightKg; }
    public void setTargetWeightKg(Double targetWeightKg) { this.targetWeightKg = targetWeightKg; }

    public Double getBmi() { return bmi; }
    public void setBmi(Double bmi) { this.bmi = bmi; }

    public String getBmiCategory() { return bmiCategory; }
    public void setBmiCategory(String bmiCategory) { this.bmiCategory = bmiCategory; }

    public String getFitnessGoalDisplay() { return fitnessGoalDisplay; }
    public void setFitnessGoalDisplay(String fitnessGoalDisplay) { this.fitnessGoalDisplay = fitnessGoalDisplay; }

    public Integer getDailyCalorieTarget() { return dailyCalorieTarget; }
    public void setDailyCalorieTarget(Integer dailyCalorieTarget) { this.dailyCalorieTarget = dailyCalorieTarget; }

    public Integer getProteinTargetG() { return proteinTargetG; }
    public void setProteinTargetG(Integer proteinTargetG) { this.proteinTargetG = proteinTargetG; }

    public Integer getWaterTargetMl() { return waterTargetMl; }
    public void setWaterTargetMl(Integer waterTargetMl) { this.waterTargetMl = waterTargetMl; }

    public boolean isWorkoutCompleted() { return workoutCompleted; }
    public void setWorkoutCompleted(boolean workoutCompleted) { this.workoutCompleted = workoutCompleted; }

    public double getWorkoutCompletionPercentage() { return workoutCompletionPercentage; }
    public void setWorkoutCompletionPercentage(double workoutCompletionPercentage) { this.workoutCompletionPercentage = workoutCompletionPercentage; }

    public String getTodayWorkoutTitle() { return todayWorkoutTitle; }
    public void setTodayWorkoutTitle(String todayWorkoutTitle) { this.todayWorkoutTitle = todayWorkoutTitle; }

    public Double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(Double caloriesBurned) { this.caloriesBurned = caloriesBurned; }

    public Double getCaloriesConsumed() { return caloriesConsumed; }
    public void setCaloriesConsumed(Double caloriesConsumed) { this.caloriesConsumed = caloriesConsumed; }

    public Double getRemainingCalories() { return remainingCalories; }
    public void setRemainingCalories(Double remainingCalories) { this.remainingCalories = remainingCalories; }

    public Integer getWaterConsumedMl() { return waterConsumedMl; }
    public void setWaterConsumedMl(Integer waterConsumedMl) { this.waterConsumedMl = waterConsumedMl; }

    public double getWaterPercentage() { return waterPercentage; }
    public void setWaterPercentage(double waterPercentage) { this.waterPercentage = waterPercentage; }

    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }

    public Integer getStepsCount() { return stepsCount; }
    public void setStepsCount(Integer stepsCount) { this.stepsCount = stepsCount; }

    public long getCurrentStreakDays() { return currentStreakDays; }
    public void setCurrentStreakDays(long currentStreakDays) { this.currentStreakDays = currentStreakDays; }

    public int getWeeklyWorkoutsCompleted() { return weeklyWorkoutsCompleted; }
    public void setWeeklyWorkoutsCompleted(int weeklyWorkoutsCompleted) { this.weeklyWorkoutsCompleted = weeklyWorkoutsCompleted; }

    public double getWeeklyCaloriesBurned() { return weeklyCaloriesBurned; }
    public void setWeeklyCaloriesBurned(double weeklyCaloriesBurned) { this.weeklyCaloriesBurned = weeklyCaloriesBurned; }

    public double getWeeklyAvgCaloriesConsumed() { return weeklyAvgCaloriesConsumed; }
    public void setWeeklyAvgCaloriesConsumed(double weeklyAvgCaloriesConsumed) { this.weeklyAvgCaloriesConsumed = weeklyAvgCaloriesConsumed; }

    public double getWeightChangeKg() { return weightChangeKg; }
    public void setWeightChangeKg(double weightChangeKg) { this.weightChangeKg = weightChangeKg; }

    public List<DailyProgressDto> getRecentProgress() { return recentProgress; }
    public void setRecentProgress(List<DailyProgressDto> recentProgress) { this.recentProgress = recentProgress; }
}
