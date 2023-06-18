package com.example.cinemavillage.service;

import com.example.cinemavillage.model.*;
import com.example.cinemavillage.repository.RoomRepository;
import com.example.cinemavillage.repository.ScreeningRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public List<ScreeningDto> findAllScreenings() {
        List<Screening> screenings = screeningRepository.findAll();
        return screenings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ScreeningDto findScreeningById(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screening not found with id " + id));
        return mapToDto(screening);
    }

    public List<ScreeningDto> findScreeningsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
        List<Screening> screenings = screeningRepository.findScreeningsByDay(startOfDay, endOfDay);
        return screenings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ScreeningDto mapToDto(Screening screening) {
        ScreeningDto screeningDto = new ScreeningDto();
        screeningDto.setId(screening.getId());
        screeningDto.setMovie(screening.getMovie());
        screeningDto.setRoomId(screening.getRoom().getId());
        screeningDto.setScreeningTime(screening.getScreeningTime());
        return screeningDto;
    }

    public AvailableSeatsDto getAvailableSeats(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new RuntimeException("Screening with id " + screeningId + " not found"));

        int normalSeats = 0;
        int premiumSeats = 0;

        for (Row row : screening.getRoom().getRows()) {
            for (Seat seat : row.getSeats()) {
                if (!seat.getReserved()) {
                    if ("normal".equals(seat.getType())) {
                        normalSeats++;
                    } else if ("premium".equals(seat.getType())) {
                        premiumSeats++;
                    }
                }
            }
        }

        return new AvailableSeatsDto(normalSeats, premiumSeats);
    }
}
