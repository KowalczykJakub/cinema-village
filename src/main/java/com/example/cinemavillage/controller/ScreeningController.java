package com.example.cinemavillage.controller;

import com.example.cinemavillage.model.Movie;
import com.example.cinemavillage.model.Room;
import com.example.cinemavillage.model.Screening;
import com.example.cinemavillage.service.RoomService;
import com.example.cinemavillage.service.ScreeningService;
import org.springframework.web.bind.annotation.*;

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
    public List<Screening> getScreenings() {
        return screeningService.findAllScreenings();
    }

    @GetMapping("/{id}")
    public Screening getScreening(@PathVariable Long id) {
        return screeningService.findScreeningById(id);
    }
}
