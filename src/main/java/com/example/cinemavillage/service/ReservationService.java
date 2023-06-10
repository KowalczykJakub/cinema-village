package com.example.cinemavillage.service;

import com.example.cinemavillage.model.Reservation;
import com.example.cinemavillage.model.Screening;
import com.example.cinemavillage.model.Seat;
import com.example.cinemavillage.repository.ReservationRepository;
import com.example.cinemavillage.repository.ScreeningRepository;
import com.example.cinemavillage.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

@Service
public class ReservationService {

    private SeatRepository seatRepository;
    private ScreeningRepository screeningRepository;
    private ReservationRepository reservationRepository;

    public ReservationService(SeatRepository seatRepository, ScreeningRepository screeningRepository, ReservationRepository reservationRepository) {
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public void reserveSeat(Long screeningId, Long roomId, Integer rowNumber, Integer seatNumber) {
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new RuntimeException("Screening does not exist."));

        Seat seat = seatRepository.findByRowRoomIdAndRowRowNumberAndSeatNumber(roomId, rowNumber, seatNumber);

        if (seat == null) {
            throw new RuntimeException("Seat does not exist.");
        }

        Reservation existingReservation = reservationRepository.findByScreeningAndSeat(screening, seat);
        if (existingReservation != null) {
            throw new RuntimeException("Seat is already reserved for this screening.");
        }

        Reservation reservation = new Reservation();
        reservation.setScreening(screening);
        reservation.setSeat(seat);
        reservationRepository.save(reservation);
    }
}
