package com.example.cinemavillage.controller;

import com.example.cinemavillage.model.ReservationRequest;
import com.example.cinemavillage.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<String> makeReservation(@RequestBody ReservationRequest request) {
        try {
            reservationService.reserveSeat(request.getScreeningId(), request.getRowNumber(), request.getSeatNumber());
            return new ResponseEntity<>("Reservation made successfully.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
