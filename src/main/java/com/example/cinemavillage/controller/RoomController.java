package com.example.cinemavillage.controller;

import com.example.cinemavillage.model.*;
import com.example.cinemavillage.repository.ReservationRepository;
import com.example.cinemavillage.service.RoomService;
import com.example.cinemavillage.service.ScreeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;
    private final ScreeningService screeningService;
    private final ReservationRepository reservationRepository;

    public RoomController(RoomService roomService, ScreeningService screeningService, ReservationRepository reservationRepository) {
        this.roomService = roomService;
        this.screeningService = screeningService;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/{id}/screening/{screeningId}")
    public ResponseEntity<Room> getRoomForScreening(@PathVariable Long id, @PathVariable Long screeningId) {
        Screening screening = screeningService.findScreeningById(screeningId);

        Room room = roomService.findRoomById(id);

        for (Row row : room.getRows()) {
            for (Seat seat : row.getSeats()) {
                Reservation existingReservation = reservationRepository.findByScreeningAndSeat(screening, seat);
                seat.setReserved(existingReservation != null);
            }
        }
        return ResponseEntity.ok(room);
    }
}
