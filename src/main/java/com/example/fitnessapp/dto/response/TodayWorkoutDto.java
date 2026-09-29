package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.WorkoutLog;
import com.example.fitnessapp.enums.WorkoutStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TodayWorkoutDto {
    private Long workoutLogId;
    private Long workoutDayId;
    private LocalDate date;
    private Integer dayOfWeek;
    private String dayName;
    private String title;
    private String focusArea;
    private boolean isRestDay;
    private WorkoutStatus status;
    private Integer totalDurationMin;
    private Double totalCaloriesBurned;
    private double completionPercentage;
    private List<WorkoutExerciseLogDto> exercises = new ArrayList<>();

    public TodayWorkoutDto() {}

    public TodayWorkoutDto(WorkoutLog log, Integer dayOfWeek, String dayName, String focusArea, boolean isRestDay) {
        if (log != null) {
            this.workoutLogId = log.getId();
            this.date = log.getLogDate();
            this.title = log.getWorkoutTitle();
            this.status = log.getStatus();
            this.totalDurationMin = log.getTotalDurationMin();
            this.totalCaloriesBurned = log.getTotalCaloriesBurned();
            if (log.getWorkoutDay() != null) {
                this.workoutDayId = log.getWorkoutDay().getId();
                this.isRestDay = log.getWorkoutDay().isRestDay();
                this.focusArea = log.getWorkoutDay().getFocusArea();
            } else {
                this.isRestDay = isRestDay;
                this.focusArea = focusArea;
            }
            this.dayOfWeek = dayOfWeek;
            this.dayName = dayName;

            if (log.getExerciseLogs() != null && !log.getExerciseLogs().isEmpty()) {
                this.exercises = log.getExerciseLogs().stream()
                        .map(WorkoutExerciseLogDto::new)
                        .collect(Collectors.toList());

                long completedCount = log.getExerciseLogs().stream()
                        .filter(el -> el.getStatus() == WorkoutStatus.COMPLETED)
                        .count();
                this.completionPercentage = Math.round(((double) completedCount / log.getExerciseLogs().size()) * 100.0);
            } else {
                this.completionPercentage = log.getStatus() == WorkoutStatus.COMPLETED ? 100.0 : 0.0;
            }
        }
    }

    public Long getWorkoutLogId() { return workoutLogId; }
    public void setWorkoutLogId(Long workoutLogId) { this.workoutLogId = workoutLogId; }

    public Long getWorkoutDayId() { return workoutDayId; }
    public void setWorkoutDayId(Long workoutDayId) { this.workoutDayId = workoutDayId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

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

    public WorkoutStatus getStatus() { return status; }
    public void setStatus(WorkoutStatus status) { this.status = status; }

    public Integer getTotalDurationMin() { return totalDurationMin; }
    public void setTotalDurationMin(Integer totalDurationMin) { this.totalDurationMin = totalDurationMin; }

    public Double getTotalCaloriesBurned() { return totalCaloriesBurned; }
    public void setTotalCaloriesBurned(Double totalCaloriesBurned) { this.totalCaloriesBurned = totalCaloriesBurned; }

    public double getCompletionPercentage() { return completionPercentage; }
    public void setCompletionPercentage(double completionPercentage) { this.completionPercentage = completionPercentage; }

    public List<WorkoutExerciseLogDto> getExercises() { return exercises; }
    public void setExercises(List<WorkoutExerciseLogDto> exercises) { this.exercises = exercises; }
}
