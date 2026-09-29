package com.example.fitnessapp.entity;

import com.example.fitnessapp.enums.FitnessExperience;
import jakarta.persistence.*;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String category; // Strength, Cardio, Bodyweight, HIIT, Flexibility

    @Column(nullable = false, length = 50)
    private String targetMuscleGroup; // Chest, Back, Legs, Shoulders, Biceps, Triceps, Core, Full Body

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FitnessExperience difficulty = FitnessExperience.BEGINNER;

    @Column(nullable = false)
    private Double metValue = 5.0; // Default MET value for moderate resistance training

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(length = 100)
    private String equipment; // Barbell, Dumbbells, Machine, Bodyweight, Cable

    private boolean isCustom = false;

    public Exercise() {}

    public Exercise(String name, String category, String targetMuscleGroup, FitnessExperience difficulty, Double metValue, String instructions, String equipment) {
        this.name = name;
        this.category = category;
        this.targetMuscleGroup = targetMuscleGroup;
        this.difficulty = difficulty;
        this.metValue = metValue;
        this.instructions = instructions;
        this.equipment = equipment;
        this.isCustom = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTargetMuscleGroup() { return targetMuscleGroup; }
    public void setTargetMuscleGroup(String targetMuscleGroup) { this.targetMuscleGroup = targetMuscleGroup; }

    public FitnessExperience getDifficulty() { return difficulty; }
    public void setDifficulty(FitnessExperience difficulty) { this.difficulty = difficulty; }

    public Double getMetValue() { return metValue; }
    public void setMetValue(Double metValue) { this.metValue = metValue; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    public boolean isCustom() { return isCustom; }
    public void setCustom(boolean custom) { isCustom = custom; }
}
