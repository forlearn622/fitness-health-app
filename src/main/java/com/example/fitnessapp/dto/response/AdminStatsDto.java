package com.example.fitnessapp.dto.response;

public class AdminStatsDto {
    private long totalUsers;
    private long activeUsers;
    private long totalWorkoutsLogged;
    private long totalExercises;
    private long totalFoods;
    private long totalGoalsTracked;

    public AdminStatsDto() {}

    public AdminStatsDto(long totalUsers, long activeUsers, long totalWorkoutsLogged, long totalExercises, long totalFoods, long totalGoalsTracked) {
        this.totalUsers = totalUsers;
        this.activeUsers = activeUsers;
        this.totalWorkoutsLogged = totalWorkoutsLogged;
        this.totalExercises = totalExercises;
        this.totalFoods = totalFoods;
        this.totalGoalsTracked = totalGoalsTracked;
    }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getActiveUsers() { return activeUsers; }
    public void setActiveUsers(long activeUsers) { this.activeUsers = activeUsers; }

    public long getTotalWorkoutsLogged() { return totalWorkoutsLogged; }
    public void setTotalWorkoutsLogged(long totalWorkoutsLogged) { this.totalWorkoutsLogged = totalWorkoutsLogged; }

    public long getTotalExercises() { return totalExercises; }
    public void setTotalExercises(long totalExercises) { this.totalExercises = totalExercises; }

    public long getTotalFoods() { return totalFoods; }
    public void setTotalFoods(long totalFoods) { this.totalFoods = totalFoods; }

    public long getTotalGoalsTracked() { return totalGoalsTracked; }
    public void setTotalGoalsTracked(long totalGoalsTracked) { this.totalGoalsTracked = totalGoalsTracked; }
}
