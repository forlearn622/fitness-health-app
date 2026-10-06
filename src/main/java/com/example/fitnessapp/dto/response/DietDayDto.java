package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.DietDay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DietDayDto {
    private Long id;
    private Integer dayOfWeek;
    private String dayName;
    private Double totalCalories = 0.0;
    private Double totalProtein = 0.0;
    private Double totalCarbs = 0.0;
    private Double totalFat = 0.0;
    private List<MealDto> meals = new ArrayList<>();

    public DietDayDto() {}

    public DietDayDto(DietDay day) {
        if (day != null) {
            this.id = day.getId();
            this.dayOfWeek = day.getDayOfWeek();
            this.dayName = getDayNameFromInt(day.getDayOfWeek());
            if (day.getMeals() != null) {
                this.meals = day.getMeals().stream()
                        .map(MealDto::new)
                        .collect(Collectors.toList());

                this.totalCalories = day.getMeals().stream()
                        .mapToDouble(m -> m.getTotalCalories() != null ? m.getTotalCalories() : 0.0)
                        .sum();
                this.totalProtein = day.getMeals().stream()
                        .mapToDouble(m -> m.getTotalProtein() != null ? m.getTotalProtein() : 0.0)
                        .sum();
                this.totalCarbs = day.getMeals().stream()
                        .mapToDouble(m -> m.getTotalCarbs() != null ? m.getTotalCarbs() : 0.0)
                        .sum();
                this.totalFat = day.getMeals().stream()
                        .mapToDouble(m -> m.getTotalFat() != null ? m.getTotalFat() : 0.0)
                        .sum();
            }
        }
    }

    private String getDayNameFromInt(Integer day) {
        if (day == null) return "";
        return switch (day) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            case 7 -> "Sunday";
            default -> "Day " + day;
        };
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getDayName() { return dayName; }
    public void setDayName(String dayName) { this.dayName = dayName; }

    public Double getTotalCalories() { return totalCalories; }
    public void setTotalCalories(Double totalCalories) { this.totalCalories = totalCalories; }

    public Double getTotalProtein() { return totalProtein; }
    public void setTotalProtein(Double totalProtein) { this.totalProtein = totalProtein; }

    public Double getTotalCarbs() { return totalCarbs; }
    public void setTotalCarbs(Double totalCarbs) { this.totalCarbs = totalCarbs; }

    public Double getTotalFat() { return totalFat; }
    public void setTotalFat(Double totalFat) { this.totalFat = totalFat; }

    public List<MealDto> getMeals() { return meals; }
    public void setMeals(List<MealDto> meals) { this.meals = meals; }
}
