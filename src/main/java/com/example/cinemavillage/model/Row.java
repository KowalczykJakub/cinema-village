package com.example.cinemavillage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cinema_row")
public class Row {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_no")
    private Integer rowNumber;

    private Boolean shift;

    @ManyToOne
    @JoinColumn(name="room_id")
    @JsonIgnore
    private Room room;

    @OneToMany(mappedBy = "row", cascade = CascadeType.ALL)
    private List<Seat> seats;

    public Row() {
    }

    public Row(Long id, Integer rowNumber, Boolean shift, Room room, List<Seat> seats) {
        this.id = id;
        this.rowNumber = rowNumber;
        this.shift = shift;
        this.room = room;
        this.seats = seats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Boolean getShift() {
        return shift;
    }

    public void setShift(Boolean shift) {
        this.shift = shift;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
