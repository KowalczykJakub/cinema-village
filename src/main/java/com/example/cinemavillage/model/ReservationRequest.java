package com.example.cinemavillage.model;

public class ReservationRequest {
    private Long screeningId;
    private Integer rowNumber;
    private Integer seatNumber;

    public ReservationRequest() {
    }

    public ReservationRequest(Long screeningId, Integer rowNumber, Integer seatNumber) {
        this.screeningId = screeningId;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Long screeningId) {
        this.screeningId = screeningId;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}
