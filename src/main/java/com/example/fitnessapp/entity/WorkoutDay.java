package com.example.fitnessapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_days")
public class WorkoutDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id", nullable = false)
    private WorkoutPlan workoutPlan;

    @Column(nullable = false)
    private Integer dayOfWeek; // 1 = Monday, ..., 7 = Sunday

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 100)
    private String focusArea;

    @Column(nullable = false)
    private boolean isRestDay = false;

    @OneToMany(mappedBy = "workoutDay", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<WorkoutExercise> workoutExercises = new ArrayList<>();

    public WorkoutDay() {}

    public WorkoutDay(Integer dayOfWeek, String title, String focusArea, boolean isRestDay) {
        this.dayOfWeek = dayOfWeek;
        this.title = title;
        this.focusArea = focusArea;
        this.isRestDay = isRestDay;
    }

    public void addWorkoutExercise(WorkoutExercise we) {
        workoutExercises.add(we);
        we.setWorkoutDay(this);
    }

    public void removeWorkoutExercise(WorkoutExercise we) {
        workoutExercises.remove(we);
        we.setWorkoutDay(null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public WorkoutPlan getWorkoutPlan() { return workoutPlan; }
    public void setWorkoutPlan(WorkoutPlan workoutPlan) { this.workoutPlan = workoutPlan; }

    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getFocusArea() { return focusArea; }
    public void setFocusArea(String focusArea) { this.focusArea = focusArea; }

    public boolean isRestDay() { return isRestDay; }
    public void setRestDay(boolean restDay) { isRestDay = restDay; }

    public List<WorkoutExercise> getWorkoutExercises() { return workoutExercises; }
    public void setWorkoutExercises(List<WorkoutExercise> workoutExercises) { this.workoutExercises = workoutExercises; }
}
