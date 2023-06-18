package com.example.cinemavillage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "position_in_row")
    private Integer positionInRow;

    @Column(name = "is_reserved", nullable = false)
    private Boolean isReserved = false;

    private String type;

    @ManyToOne
    @JoinColumn(name = "row_id")
    @JsonIgnore
    private Row row;

    public Seat() {
    }

    public Seat(Long id, Integer seatNumber, Integer positionInRow, Boolean isReserved, String type, Row row) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.positionInRow = positionInRow;
        this.isReserved = isReserved;
        this.type = type;
        this.row = row;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getPositionInRow() {
        return positionInRow;
    }

    public void setPositionInRow(Integer positionInRow) {
        this.positionInRow = positionInRow;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }
}
