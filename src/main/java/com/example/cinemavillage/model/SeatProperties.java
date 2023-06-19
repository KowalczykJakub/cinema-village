package com.example.cinemavillage.model;

public class SeatProperties {
    private Integer rowNumber;
    private Integer seatNumber;

    public SeatProperties() {
    }

    public SeatProperties(Integer rowNumber, Integer seatNumber) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
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
