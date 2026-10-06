package com.example.fitnessapp.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyDietSummaryDto {
    private LocalDate logDate;
    private Integer calorieTarget = 2000;
    private Double caloriesConsumed = 0.0;
    private Double remainingCalories = 2000.0;
    private Integer proteinTargetG = 120;
    private Double proteinConsumed = 0.0;
    private Double remainingProtein = 120.0;
    private Integer carbsTargetG = 250;
    private Double carbsConsumed = 0.0;
    private Double remainingCarbs = 250.0;
    private Integer fatTargetG = 55;
    private Double fatConsumed = 0.0;
    private Double remainingFat = 55.0;
    private List<FoodLogDto> loggedFoods = new ArrayList<>();

    public DailyDietSummaryDto() {}

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public Integer getCalorieTarget() { return calorieTarget; }
    public void setCalorieTarget(Integer calorieTarget) { this.calorieTarget = calorieTarget; }

    public Double getCaloriesConsumed() { return caloriesConsumed; }
    public void setCaloriesConsumed(Double caloriesConsumed) { this.caloriesConsumed = caloriesConsumed; }

    public Double getRemainingCalories() { return remainingCalories; }
    public void setRemainingCalories(Double remainingCalories) { this.remainingCalories = remainingCalories; }

    public Integer getProteinTargetG() { return proteinTargetG; }
    public void setProteinTargetG(Integer proteinTargetG) { this.proteinTargetG = proteinTargetG; }

    public Double getProteinConsumed() { return proteinConsumed; }
    public void setProteinConsumed(Double proteinConsumed) { this.proteinConsumed = proteinConsumed; }

    public Double getRemainingProtein() { return remainingProtein; }
    public void setRemainingProtein(Double remainingProtein) { this.remainingProtein = remainingProtein; }

    public Integer getCarbsTargetG() { return carbsTargetG; }
    public void setCarbsTargetG(Integer carbsTargetG) { this.carbsTargetG = carbsTargetG; }

    public Double getCarbsConsumed() { return carbsConsumed; }
    public void setCarbsConsumed(Double carbsConsumed) { this.carbsConsumed = carbsConsumed; }

    public Double getRemainingCarbs() { return remainingCarbs; }
    public void setRemainingCarbs(Double remainingCarbs) { this.remainingCarbs = remainingCarbs; }

    public Integer getFatTargetG() { return fatTargetG; }
    public void setFatTargetG(Integer fatTargetG) { this.fatTargetG = fatTargetG; }

    public Double getFatConsumed() { return fatConsumed; }
    public void setFatConsumed(Double fatConsumed) { this.fatConsumed = fatConsumed; }

    public Double getRemainingFat() { return remainingFat; }
    public void setRemainingFat(Double remainingFat) { this.remainingFat = remainingFat; }

    public List<FoodLogDto> getLoggedFoods() { return loggedFoods; }
    public void setLoggedFoods(List<FoodLogDto> loggedFoods) { this.loggedFoods = loggedFoods; }
}
