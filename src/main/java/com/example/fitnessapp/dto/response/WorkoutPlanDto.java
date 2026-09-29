package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.WorkoutPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutPlanDto {
    private Long id;
    private String title;
    private String description;
    private String goal;
    private String experienceLevel;
    private boolean isActive;
    private List<WorkoutDayDto> days = new ArrayList<>();

    public WorkoutPlanDto() {}

    public WorkoutPlanDto(WorkoutPlan plan) {
        if (plan != null) {
            this.id = plan.getId();
            this.title = plan.getTitle();
            this.description = plan.getDescription();
            this.goal = plan.getGoal() != null ? plan.getGoal().getDisplayName() : null;
            this.experienceLevel = plan.getExperienceLevel() != null ? plan.getExperienceLevel().name() : null;
            this.isActive = plan.isActive();
            if (plan.getWorkoutDays() != null) {
                this.days = plan.getWorkoutDays().stream()
                        .map(WorkoutDayDto::new)
                        .collect(Collectors.toList());
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public List<WorkoutDayDto> getDays() { return days; }
    public void setDays(List<WorkoutDayDto> days) { this.days = days; }
}
