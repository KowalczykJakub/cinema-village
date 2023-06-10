package com.example.cinemavillage.repository;

import com.example.cinemavillage.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findByRowRoomIdAndRowRowNumberAndSeatNumber(Long roomId, Integer rowNumber, Integer seatNumber);
}
