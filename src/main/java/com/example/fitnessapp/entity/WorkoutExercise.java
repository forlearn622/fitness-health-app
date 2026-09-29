package com.example.fitnessapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "workout_exercises")
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_day_id", nullable = false)
    private WorkoutDay workoutDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private Integer sets = 3;

    @Column(nullable = false)
    private Integer reps = 10;

    @Column(nullable = false)
    private Integer restSeconds = 60;

    @Column(nullable = false)
    private Integer orderIndex = 0;

    private Integer estimatedDurationMin = 10;
    private Double estimatedCalories = 50.0;

    public WorkoutExercise() {}

    public WorkoutExercise(Exercise exercise, Integer sets, Integer reps, Integer restSeconds, Integer orderIndex, Integer estimatedDurationMin, Double estimatedCalories) {
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
        this.orderIndex = orderIndex;
        this.estimatedDurationMin = estimatedDurationMin;
        this.estimatedCalories = estimatedCalories;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public WorkoutDay getWorkoutDay() { return workoutDay; }
    public void setWorkoutDay(WorkoutDay workoutDay) { this.workoutDay = workoutDay; }

    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }

    public Integer getSets() { return sets; }
    public void setSets(Integer sets) { this.sets = sets; }

    public Integer getReps() { return reps; }
    public void setReps(Integer reps) { this.reps = reps; }

    public Integer getRestSeconds() { return restSeconds; }
    public void setRestSeconds(Integer restSeconds) { this.restSeconds = restSeconds; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public Integer getEstimatedDurationMin() { return estimatedDurationMin; }
    public void setEstimatedDurationMin(Integer estimatedDurationMin) { this.estimatedDurationMin = estimatedDurationMin; }

    public Double getEstimatedCalories() { return estimatedCalories; }
    public void setEstimatedCalories(Double estimatedCalories) { this.estimatedCalories = estimatedCalories; }
}
