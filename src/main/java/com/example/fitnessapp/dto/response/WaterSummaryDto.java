package com.example.fitnessapp.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WaterSummaryDto {
    private LocalDate logDate;
    private Integer targetMl = 3000;
    private Integer consumedMl = 0;
    private Integer remainingMl = 3000;
    private double progressPercentage = 0.0;
    private List<WaterLogDto> logs = new ArrayList<>();

    public WaterSummaryDto() {}

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public Integer getTargetMl() { return targetMl; }
    public void setTargetMl(Integer targetMl) { this.targetMl = targetMl; }

    public Integer getConsumedMl() { return consumedMl; }
    public void setConsumedMl(Integer consumedMl) { this.consumedMl = consumedMl; }

    public Integer getRemainingMl() { return remainingMl; }
    public void setRemainingMl(Integer remainingMl) { this.remainingMl = remainingMl; }

    public double getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(double progressPercentage) { this.progressPercentage = progressPercentage; }

    public List<WaterLogDto> getLogs() { return logs; }
    public void setLogs(List<WaterLogDto> logs) { this.logs = logs; }
}
