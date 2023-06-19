package com.example.cinemavillage.model;

import java.util.List;

public class ReservationRequest {
    private Long screeningId;
    private List<SeatProperties> seatProperties;
    private PersonalInfo personalInfo;

    public ReservationRequest() {
    }

    public ReservationRequest(Long screeningId, List<SeatProperties> seatProperties, PersonalInfo personalInfo) {
        this.screeningId = screeningId;
        this.seatProperties = seatProperties;
        this.personalInfo = personalInfo;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Long screeningId) {
        this.screeningId = screeningId;
    }

    public List<SeatProperties> getSeatProperties() {
        return seatProperties;
    }

    public void setSeatProperties(List<SeatProperties> seatProperties) {
        this.seatProperties = seatProperties;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }
}
