package com.example.cinemavillage.service;

import com.example.cinemavillage.PdfGenerator;
import com.example.cinemavillage.model.Reservation;
import com.example.cinemavillage.model.ReservationRequest;
import com.example.cinemavillage.model.Screening;
import com.example.cinemavillage.model.Seat;
import com.example.cinemavillage.repository.ReservationRepository;
import com.example.cinemavillage.repository.ScreeningRepository;
import com.example.cinemavillage.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private SeatRepository seatRepository;
    private ScreeningRepository screeningRepository;
    private ReservationRepository reservationRepository;
    private EmailService emailService;

    public ReservationService(SeatRepository seatRepository, ScreeningRepository screeningRepository, ReservationRepository reservationRepository, EmailService emailService) {
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.reservationRepository = reservationRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void reserveSeat(ReservationRequest request) {
        Screening screening = screeningRepository.findById(request.getScreeningId())
                .orElseThrow(() -> new RuntimeException("Screening does not exist."));

        Seat seat = seatRepository.findByRowRoomIdAndRowRowNumberAndSeatNumber(screening.getRoom().getId(), request.getRowNumber(), request.getSeatNumber());

        List<String> emailElements = new ArrayList<>();
        emailElements.add(screening.getMovie().getTitle());
        emailElements.add(screening.getScreeningTime().toString());
        emailElements.add(request.getPersonalInfo().getFirstName());
        emailElements.add(request.getPersonalInfo().getLastName());
        emailElements.add(request.getPersonalInfo().getPhoneNumber());
        emailElements.add(request.getPersonalInfo().getEmail());
        emailElements.add("Sala - " + screening.getRoom().getId());
        emailElements.add("Rzad - " + request.getRowNumber().toString());
        emailElements.add("Numer miejsca - " + request.getSeatNumber().toString());

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

        String filePath = "src/main/resources/static/confirmation.pdf";

        try {
            PdfGenerator.generatePdf(filePath, emailElements, screening.getMovie().getPosterPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        emailService.sendMailWithAttachment(
                request.getPersonalInfo().getEmail(),
                "Potwierdzenie rezerwacji",
                "W zalaczniku znajduje sie potwierdzenie rezerwacji",
                filePath
        );
    }
}
