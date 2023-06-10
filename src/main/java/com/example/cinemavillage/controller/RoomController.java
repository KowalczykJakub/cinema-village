package com.example.cinemavillage.controller;

import com.example.cinemavillage.model.Movie;
import com.example.cinemavillage.model.ReservationRequest;
import com.example.cinemavillage.model.Room;
import com.example.cinemavillage.repository.RoomRepository;
import com.example.cinemavillage.service.ReservationService;
import com.example.cinemavillage.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getRooms() {
        return roomService.findAllRooms();
    }
}
