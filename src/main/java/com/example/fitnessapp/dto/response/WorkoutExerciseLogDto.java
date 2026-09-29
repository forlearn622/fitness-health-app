package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.WorkoutExerciseLog;
import com.example.fitnessapp.enums.WorkoutStatus;

public class WorkoutExerciseLogDto {
    private Long id;
    private Long exerciseId;
    private String exerciseName;
    private String category;
    private String targetMuscleGroup;
    private Integer setsPlanned;
    private Integer setsCompleted;
    private Integer repsPlanned;
    private Integer repsCompleted;
    private Double weightUsedKg;
    private Double caloriesBurned;
    private WorkoutStatus status;
    private String instructions;

    public WorkoutExerciseLogDto() {}

    public WorkoutExerciseLogDto(WorkoutExerciseLog log) {
        if (log != null) {
            this.id = log.getId();
            this.exerciseName = log.getExerciseName();
            this.setsPlanned = log.getSetsPlanned();
            this.setsCompleted = log.getSetsCompleted();
            this.repsPlanned = log.getRepsPlanned();
            this.repsCompleted = log.getRepsCompleted();
            this.weightUsedKg = log.getWeightUsedKg();
            this.caloriesBurned = log.getCaloriesBurned();
            this.status = log.getStatus();
            if (log.getExercise() != null) {
                this.exerciseId = log.getExercise().getId();
                this.category = log.getExercise().getCategory();
                this.targetMuscleGroup = log.getExercise().getTargetMuscleGroup();
                this.instructions = log.getExercise().getInstructions();
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTargetMuscleGroup() { return targetMuscleGroup; }
    public void setTargetMuscleGroup(String targetMuscleGroup) { this.targetMuscleGroup = targetMuscleGroup; }

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

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}
