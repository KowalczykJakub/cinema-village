package com.example.cinemavillage.service;

import com.example.cinemavillage.model.*;
import com.example.cinemavillage.repository.ReservationRepository;
import com.example.cinemavillage.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final ReservationRepository reservationRepository;

    public ScreeningService(ScreeningRepository screeningRepository, ReservationRepository reservationRepository) {
        this.screeningRepository = screeningRepository;
        this.reservationRepository = reservationRepository;
    }

    public Screening findScreeningById(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screening not found with id " + id));
    }

    public AvailableSeatsDto getAvailableSeats(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new RuntimeException("Screening with id " + screeningId + " not found"));

        int normalSeats = 0;
        int premiumSeats = 0;

        for (Row row : screening.getRoom().getRows()) {
            for (Seat seat : row.getSeats()) {
                Reservation existingReservation = reservationRepository.findByScreeningAndSeat(screening, seat);

                if (existingReservation == null) {
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

    public List<MovieInfoDto> findMoviesWithScreeningsByDate(LocalDate date) {
        List<Screening> screenings = findScreeningsByDate(date);
        Map<Long, List<ScreeningInfoDto>> screeningsByMovieId = screenings.stream()
                .collect(Collectors.groupingBy(screening -> screening.getMovie().getId(),
                        Collectors.mapping(this::convertToScreeningInfoDto, Collectors.toList())));
        return screenings.stream()
                .map(Screening::getMovie)
                .distinct()
                .map(movie -> convertToMovieDto(movie, screeningsByMovieId.get(movie.getId())))
                .collect(Collectors.toList());
    }

    public List<Screening> findScreeningsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
        return screeningRepository.findScreeningsByDay(startOfDay, endOfDay);
    }

    private ScreeningInfoDto convertToScreeningInfoDto(Screening screening) {
        ScreeningInfoDto dto = new ScreeningInfoDto();
        dto.setScreeningId(screening.getId());
        dto.setTime(screening.getScreeningTime().toLocalTime());
        return dto;
    }

    private MovieInfoDto convertToMovieDto(Movie movie, List<ScreeningInfoDto> screenings) {
        MovieInfoDto dto = new MovieInfoDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setOverview(movie.getOverview());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setRuntime(movie.getRuntime());
        dto.setPosterPath(movie.getPosterPath());
        dto.setScreenings(screenings);
        return dto;
    }
}
