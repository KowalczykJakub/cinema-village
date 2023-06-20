package com.example.cinemavillage.service;

import com.example.cinemavillage.PdfGenerator;
import com.example.cinemavillage.model.*;
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

        List<SeatProperties> seatProps = request.getSeatProperties();
        List<Seat> seats = new ArrayList<>();

        for (SeatProperties seatProp : seatProps) {
            seats.add(seatRepository.findByRowRoomIdAndRowRowNumberAndSeatNumber(screening.getRoom().getId(), seatProp.getRowNumber(), seatProp.getSeatNumber()));
        }

        List<String> emailElements = new ArrayList<>();

        emailElements.add(screening.getMovie().getTitle());
        emailElements.add(screening.getScreeningTime().toString());
        emailElements.add(request.getPersonalInfo().getFirstName());
        emailElements.add(request.getPersonalInfo().getLastName());
        emailElements.add(request.getPersonalInfo().getPhoneNumber());
        emailElements.add(request.getPersonalInfo().getEmail());

        if (seats.size() == 0) {
            throw new RuntimeException("Seat does not exist.");
        }

        for (Seat seat : seats) {
            emailElements.add("Sala - " + screening.getRoom().getId());
            emailElements.add("Rzad - " + seat.getRow().getRowNumber().toString());
            emailElements.add("Numer miejsca - " + seat.getSeatNumber().toString());
            emailElements.add(" = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = ");

            Reservation existingReservation = reservationRepository.findByScreeningAndSeat(screening, seat);
            if (existingReservation != null) {
                throw new RuntimeException("Seat is already reserved for this screening.");
            }

            Reservation reservation = new Reservation();
            reservation.setScreening(screening);
            reservation.setSeat(seat);
            reservationRepository.save(reservation);
        }

        String filePath = "/tmp/confirmation.pdf";

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
