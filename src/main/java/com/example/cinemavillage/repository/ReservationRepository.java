package com.example.cinemavillage.repository;

import com.example.cinemavillage.model.Reservation;
import com.example.cinemavillage.model.Screening;
import com.example.cinemavillage.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByScreeningAndSeat(Screening screening, Seat seat);
}
