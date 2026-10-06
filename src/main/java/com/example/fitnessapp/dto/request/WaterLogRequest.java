package com.example.fitnessapp.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class WaterLogRequest {

    @NotNull(message = "Water amount in ml is required")
    @Positive(message = "Water amount must be positive")
    private Integer amountMl;

    private LocalDate logDate;

    public WaterLogRequest() {}

    public WaterLogRequest(Integer amountMl, LocalDate logDate) {
        this.amountMl = amountMl;
        this.logDate = logDate;
    }

    public Integer getAmountMl() { return amountMl; }
    public void setAmountMl(Integer amountMl) { this.amountMl = amountMl; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }
}
