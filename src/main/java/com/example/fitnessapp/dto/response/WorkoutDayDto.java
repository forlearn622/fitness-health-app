package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.WorkoutDay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutDayDto {
    private Long id;
    private Integer dayOfWeek;
    private String dayName;
    private String title;
    private String focusArea;
    private boolean isRestDay;
    private List<WorkoutExerciseDto> exercises = new ArrayList<>();

    public WorkoutDayDto() {}

    public WorkoutDayDto(WorkoutDay day) {
        if (day != null) {
            this.id = day.getId();
            this.dayOfWeek = day.getDayOfWeek();
            this.dayName = getDayNameFromInt(day.getDayOfWeek());
            this.title = day.getTitle();
            this.focusArea = day.getFocusArea();
            this.isRestDay = day.isRestDay();
            if (day.getWorkoutExercises() != null) {
                this.exercises = day.getWorkoutExercises().stream()
                        .map(WorkoutExerciseDto::new)
                        .collect(Collectors.toList());
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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getFocusArea() { return focusArea; }
    public void setFocusArea(String focusArea) { this.focusArea = focusArea; }

    public boolean isRestDay() { return isRestDay; }
    public void setRestDay(boolean restDay) { isRestDay = restDay; }

    public List<WorkoutExerciseDto> getExercises() { return exercises; }
    public void setExercises(List<WorkoutExerciseDto> exercises) { this.exercises = exercises; }
}
