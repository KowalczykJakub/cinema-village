package com.example.cinemavillage.model;

public class AvailableSeatsDto {

    private int normal;
    private int premium;

    public AvailableSeatsDto() {
    }

    public AvailableSeatsDto(int normal, int premium) {
        this.normal = normal;
        this.premium = premium;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }
}
