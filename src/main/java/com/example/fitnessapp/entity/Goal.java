package com.example.fitnessapp.entity;

import com.example.fitnessapp.enums.GoalStatus;
import com.example.fitnessapp.enums.GoalType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 150)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GoalType goalType;

    @Column(nullable = false)
    private Double startValue;

    @Column(nullable = false)
    private Double targetValue;

    @Column(nullable = false)
    private Double currentValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GoalStatus status = GoalStatus.IN_PROGRESS;

    private LocalDate targetDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Goal() {}

    public Goal(User user, String title, GoalType goalType, Double startValue, Double targetValue, Double currentValue, LocalDate targetDate) {
        this.user = user;
        this.title = title;
        this.goalType = goalType;
        this.startValue = startValue;
        this.targetValue = targetValue;
        this.currentValue = currentValue;
        this.targetDate = targetDate;
        this.status = GoalStatus.IN_PROGRESS;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public double getProgressPercentage() {
        if (targetValue == null || startValue == null || currentValue == null) return 0.0;
        if (startValue.equals(targetValue)) return 100.0;
        double progress = ((currentValue - startValue) / (targetValue - startValue)) * 100.0;
        return Math.max(0.0, Math.min(100.0, Math.round(progress * 10.0) / 10.0));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public GoalType getGoalType() { return goalType; }
    public void setGoalType(GoalType goalType) { this.goalType = goalType; }

    public Double getStartValue() { return startValue; }
    public void setStartValue(Double startValue) { this.startValue = startValue; }

    public Double getTargetValue() { return targetValue; }
    public void setTargetValue(Double targetValue) { this.targetValue = targetValue; }

    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }

    public GoalStatus getStatus() { return status; }
    public void setStatus(GoalStatus status) { this.status = status; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
