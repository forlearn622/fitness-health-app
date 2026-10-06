package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.WaterLog;

import java.time.LocalDateTime;

public class WaterLogDto {
    private Long id;
    private Integer amountMl;
    private LocalDateTime loggedAt;

    public WaterLogDto() {}

    public WaterLogDto(WaterLog log) {
        if (log != null) {
            this.id = log.getId();
            this.amountMl = log.getAmountMl();
            this.loggedAt = log.getLoggedAt();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getAmountMl() { return amountMl; }
    public void setAmountMl(Integer amountMl) { this.amountMl = amountMl; }

    public LocalDateTime getLoggedAt() { return loggedAt; }
    public void setLoggedAt(LocalDateTime loggedAt) { this.loggedAt = loggedAt; }
}
