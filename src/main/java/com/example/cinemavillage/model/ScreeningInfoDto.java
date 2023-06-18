package com.example.cinemavillage.model;

import java.time.LocalTime;

public class ScreeningInfoDto {
    private Long screeningId;
    private LocalTime time;

    public ScreeningInfoDto() {
    }

    public ScreeningInfoDto(Long screeningId, LocalTime time) {
        this.screeningId = screeningId;
        this.time = time;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Long screeningId) {
        this.screeningId = screeningId;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
