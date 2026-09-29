package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.WorkoutExercise;

public class WorkoutExerciseDto {
    private Long id;
    private Long exerciseId;
    private String exerciseName;
    private String category;
    private String targetMuscleGroup;
    private Integer sets;
    private Integer reps;
    private Integer restSeconds;
    private Integer orderIndex;
    private Integer estimatedDurationMin;
    private Double estimatedCalories;
    private String instructions;
    private String equipment;

    public WorkoutExerciseDto() {}

    public WorkoutExerciseDto(WorkoutExercise we) {
        if (we != null) {
            this.id = we.getId();
            this.sets = we.getSets();
            this.reps = we.getReps();
            this.restSeconds = we.getRestSeconds();
            this.orderIndex = we.getOrderIndex();
            this.estimatedDurationMin = we.getEstimatedDurationMin();
            this.estimatedCalories = we.getEstimatedCalories();
            if (we.getExercise() != null) {
                this.exerciseId = we.getExercise().getId();
                this.exerciseName = we.getExercise().getName();
                this.category = we.getExercise().getCategory();
                this.targetMuscleGroup = we.getExercise().getTargetMuscleGroup();
                this.instructions = we.getExercise().getInstructions();
                this.equipment = we.getExercise().getEquipment();
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

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }
}
