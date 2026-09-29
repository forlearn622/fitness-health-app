package com.example.fitnessapp.entity;

import com.example.fitnessapp.enums.WorkoutStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "workout_exercise_logs")
public class WorkoutExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_log_id", nullable = false)
    private WorkoutLog workoutLog;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private String exerciseName;

    private Integer setsPlanned = 3;
    private Integer setsCompleted = 0;
    private Integer repsPlanned = 10;
    private Integer repsCompleted = 0;
    private Double weightUsedKg = 0.0;
    private Double caloriesBurned = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WorkoutStatus status = WorkoutStatus.NOT_STARTED;

    public WorkoutExerciseLog() {}

    public WorkoutExerciseLog(Exercise exercise, Integer setsPlanned, Integer repsPlanned, Double caloriesBurned) {
        this.exercise = exercise;
        this.exerciseName = exercise.getName();
        this.setsPlanned = setsPlanned;
        this.repsPlanned = repsPlanned;
        this.caloriesBurned = caloriesBurned;
        this.status = WorkoutStatus.NOT_STARTED;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public WorkoutLog getWorkoutLog() { return workoutLog; }
    public void setWorkoutLog(WorkoutLog workoutLog) { this.workoutLog = workoutLog; }

    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public Integer getSetsPlanned() { return setsPlanned; }
    public void setSetsPlanned(Integer setsPlanned) { this.setsPlanned = setsPlanned; }

    public Integer getSetsCompleted() { return setsCompleted; }
    public void setSetsCompleted(Integer setsCompleted) { this.setsCompleted = setsCompleted; }

    public Integer getRepsPlanned() { return repsPlanned; }
    public void setRepsPlanned(Integer repsPlanned) { this.repsPlanned = repsPlanned; }

    public Integer getRepsCompleted() { return repsCompleted; }
    public void setRepsCompleted(Integer repsCompleted) { this.repsCompleted = repsCompleted; }

    public Double getWeightUsedKg() { return weightUsedKg; }
    public void setWeightUsedKg(Double weightUsedKg) { this.weightUsedKg = weightUsedKg; }

    public Double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(Double caloriesBurned) { this.caloriesBurned = caloriesBurned; }

    public WorkoutStatus getStatus() { return status; }
    public void setStatus(WorkoutStatus status) { this.status = status; }
}
