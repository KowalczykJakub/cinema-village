package com.example.cinemavillage.service;

import com.example.cinemavillage.model.Movie;
import com.example.cinemavillage.model.Room;
import com.example.cinemavillage.model.Screening;
import com.example.cinemavillage.repository.RoomRepository;
import com.example.cinemavillage.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public List<Screening> findAllScreenings() {
        return screeningRepository.findAll();
    }

    public Screening findScreeningById(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screening not found with id " + id));
    }
}
