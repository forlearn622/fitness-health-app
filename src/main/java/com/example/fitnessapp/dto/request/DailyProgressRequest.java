package com.example.fitnessapp.dto.request;

import java.time.LocalDate;

public class DailyProgressRequest {
    private LocalDate logDate;
    private Double weightKg;
    private Double sleepHours;
    private Integer stepsCount;
    private String notes;

    public DailyProgressRequest() {}

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }

    public Integer getStepsCount() { return stepsCount; }
    public void setStepsCount(Integer stepsCount) { this.stepsCount = stepsCount; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
