package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.DietPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DietPlanDto {
    private Long id;
    private String title;
    private Integer calorieTarget;
    private Integer proteinTargetG;
    private Integer carbsTargetG;
    private Integer fatTargetG;
    private boolean isActive;
    private List<DietDayDto> days = new ArrayList<>();

    public DietPlanDto() {}

    public DietPlanDto(DietPlan plan) {
        if (plan != null) {
            this.id = plan.getId();
            this.title = plan.getTitle();
            this.calorieTarget = plan.getCalorieTarget();
            this.proteinTargetG = plan.getProteinTargetG();
            this.carbsTargetG = plan.getCarbsTargetG();
            this.fatTargetG = plan.getFatTargetG();
            this.isActive = plan.isActive();
            if (plan.getDietDays() != null) {
                this.days = plan.getDietDays().stream()
                        .map(DietDayDto::new)
                        .collect(Collectors.toList());
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getCalorieTarget() { return calorieTarget; }
    public void setCalorieTarget(Integer calorieTarget) { this.calorieTarget = calorieTarget; }

    public Integer getProteinTargetG() { return proteinTargetG; }
    public void setProteinTargetG(Integer proteinTargetG) { this.proteinTargetG = proteinTargetG; }

    public Integer getCarbsTargetG() { return carbsTargetG; }
    public void setCarbsTargetG(Integer carbsTargetG) { this.carbsTargetG = carbsTargetG; }

    public Integer getFatTargetG() { return fatTargetG; }
    public void setFatTargetG(Integer fatTargetG) { this.fatTargetG = fatTargetG; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public List<DietDayDto> getDays() { return days; }
    public void setDays(List<DietDayDto> days) { this.days = days; }
}
