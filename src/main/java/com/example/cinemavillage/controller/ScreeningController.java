package com.example.cinemavillage.controller;

import com.example.cinemavillage.model.*;
import com.example.cinemavillage.service.RoomService;
import com.example.cinemavillage.service.ScreeningService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/screenings")
@CrossOrigin(origins = "*")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public List<ScreeningDto> getScreenings() {
        return screeningService.findAllScreenings();
    }

    @GetMapping("/{id}")
    public ScreeningDto getScreening(@PathVariable Long id) {
        return screeningService.findScreeningById(id);
    }

    @GetMapping("/date")
    public List<ScreeningDto> getDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return screeningService.findScreeningsByDate(date);
    }

    @GetMapping("/{screeningId}/available-seats")
    public AvailableSeatsDto getAvailableSeats(@PathVariable Long screeningId) {
        return screeningService.getAvailableSeats(screeningId);
    }
}
