package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.UserProfile;
import com.example.fitnessapp.enums.ActivityLevel;
import com.example.fitnessapp.enums.FitnessExperience;
import com.example.fitnessapp.enums.FitnessGoal;
import com.example.fitnessapp.enums.Gender;

public class UserProfileDto {
    private Long id;
    private Integer age;
    private Gender gender;
    private Double heightCm;
    private Double weightKg;
    private Double targetWeightKg;
    private ActivityLevel activityLevel;
    private FitnessExperience fitnessExperience;
    private FitnessGoal fitnessGoal;
    private Double bmi;
    private String bmiCategory;
    private Double bmr;
    private Double tdee;
    private Integer dailyCalorieTarget;
    private Integer proteinTargetG;
    private Integer carbsTargetG;
    private Integer fatTargetG;
    private Integer waterTargetMl;

    public UserProfileDto() {}

    public UserProfileDto(UserProfile profile) {
        if (profile != null) {
            this.id = profile.getId();
            this.age = profile.getAge();
            this.gender = profile.getGender();
            this.heightCm = profile.getHeightCm();
            this.weightKg = profile.getWeightKg();
            this.targetWeightKg = profile.getTargetWeightKg();
            this.activityLevel = profile.getActivityLevel();
            this.fitnessExperience = profile.getFitnessExperience();
            this.fitnessGoal = profile.getFitnessGoal();
            this.bmi = profile.getBmi();
            this.bmiCategory = profile.getBmiCategory();
            this.bmr = profile.getBmr();
            this.tdee = profile.getTdee();
            this.dailyCalorieTarget = profile.getDailyCalorieTarget();
            this.proteinTargetG = profile.getProteinTargetG();
            this.carbsTargetG = profile.getCarbsTargetG();
            this.fatTargetG = profile.getFatTargetG();
            this.waterTargetMl = profile.getWaterTargetMl();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public Double getTargetWeightKg() { return targetWeightKg; }
    public void setTargetWeightKg(Double targetWeightKg) { this.targetWeightKg = targetWeightKg; }

    public ActivityLevel getActivityLevel() { return activityLevel; }
    public void setActivityLevel(ActivityLevel activityLevel) { this.activityLevel = activityLevel; }

    public FitnessExperience getFitnessExperience() { return fitnessExperience; }
    public void setFitnessExperience(FitnessExperience fitnessExperience) { this.fitnessExperience = fitnessExperience; }

    public FitnessGoal getFitnessGoal() { return fitnessGoal; }
    public void setFitnessGoal(FitnessGoal fitnessGoal) { this.fitnessGoal = fitnessGoal; }

    public Double getBmi() { return bmi; }
    public void setBmi(Double bmi) { this.bmi = bmi; }

    public String getBmiCategory() { return bmiCategory; }
    public void setBmiCategory(String bmiCategory) { this.bmiCategory = bmiCategory; }

    public Double getBmr() { return bmr; }
    public void setBmr(Double bmr) { this.bmr = bmr; }

    public Double getTdee() { return tdee; }
    public void setTdee(Double tdee) { this.tdee = tdee; }

    public Integer getDailyCalorieTarget() { return dailyCalorieTarget; }
    public void setDailyCalorieTarget(Integer dailyCalorieTarget) { this.dailyCalorieTarget = dailyCalorieTarget; }

    public Integer getProteinTargetG() { return proteinTargetG; }
    public void setProteinTargetG(Integer proteinTargetG) { this.proteinTargetG = proteinTargetG; }

    public Integer getCarbsTargetG() { return carbsTargetG; }
    public void setCarbsTargetG(Integer carbsTargetG) { this.carbsTargetG = carbsTargetG; }

    public Integer getFatTargetG() { return fatTargetG; }
    public void setFatTargetG(Integer fatTargetG) { this.fatTargetG = fatTargetG; }

    public Integer getWaterTargetMl() { return waterTargetMl; }
    public void setWaterTargetMl(Integer waterTargetMl) { this.waterTargetMl = waterTargetMl; }
}
