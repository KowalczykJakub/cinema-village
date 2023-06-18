package com.example.cinemavillage.model;

import java.time.LocalDateTime;

public class ScreeningDto {

    private Long id;
    private Movie movie;
    private Long roomId;
    private LocalDateTime screeningTime;

    public ScreeningDto() {
    }

    public ScreeningDto(Long id, Movie movie, Long roomId, LocalDateTime screeningTime) {
        this.id = id;
        this.movie = movie;
        this.roomId = roomId;
        this.screeningTime = screeningTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }
}
