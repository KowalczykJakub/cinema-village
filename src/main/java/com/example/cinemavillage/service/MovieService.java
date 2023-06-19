package com.example.cinemavillage.service;

import com.example.cinemavillage.model.Movie;
import com.example.cinemavillage.model.Room;
import com.example.cinemavillage.model.Screening;
import com.example.cinemavillage.repository.MovieRepository;
import com.example.cinemavillage.repository.RoomRepository;
import com.example.cinemavillage.repository.ScreeningRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class MovieService {
    private static final String TMDB_API_KEY = "90a76673077ab70a8064d60d22cbd875";

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;

    public MovieService(MovieRepository movieRepository, ScreeningRepository screeningRepository, RoomRepository roomRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public Movie findMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + id));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fetchAndSaveMovies() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + TMDB_API_KEY + "&language=en-US&page=1";
        var moviesResponse = restTemplate.getForObject(url, LinkedHashMap.class);

        var movieResults = (List<LinkedHashMap>) moviesResponse.get("results");
        int numberOfDays = 5;

        List<Movie> savedMovies = new ArrayList<>();
        for (var movieResult : movieResults) {
            Integer movieId = (Integer) movieResult.get("id");

            url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + TMDB_API_KEY + "&language=en-US";
            var movieDetails = restTemplate.getForObject(url, LinkedHashMap.class);

            String title = (String) movieDetails.get("title");
            String overview = (String) movieDetails.get("overview");
            String releaseDate = (String) movieDetails.get("release_date");
            Integer runtime = (Integer) movieDetails.get("runtime");
            String posterPath = (String) movieResult.get("poster_path");

            url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + TMDB_API_KEY;
            var credits = restTemplate.getForObject(url, LinkedHashMap.class);
            List<LinkedHashMap> crew = (List<LinkedHashMap>) credits.get("crew");

            String director = crew.stream()
                    .filter(member -> "Directing".equals(member.get("department")) && "Director".equals(member.get("job")))
                    .map(member -> (String) member.get("name"))
                    .findFirst()
                    .orElse(null);

            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setOverview(overview);
            movie.setReleaseDate(LocalDate.parse(releaseDate));
            movie.setRuntime(runtime);
            movie.setDirector(director);
            movie.setPosterPath("https://image.tmdb.org/t/p/w500" + posterPath);

            movie = movieRepository.save(movie);
            savedMovies.add(movie);
        }

        generateAndSaveScreenings(savedMovies, numberOfDays);
    }

    private void generateAndSaveScreenings(List<Movie> movies, int numDays) {
        int moviesPerDay = 3;
        LocalDateTime now = LocalDateTime.now();
        List<Room> rooms = roomRepository.findAll();
        List<LocalTime> times = List.of(LocalTime.of(10, 0), LocalTime.of(13, 0), LocalTime.of(16, 0), LocalTime.of(20, 0));

        int index = 0;

        for (int i = 0; i < numDays; i++) {

            Collections.shuffle(movies);

            for (int j = 0; j < moviesPerDay; j++) {

                for (Room room : rooms) {

                    Movie movie = movies.get(index);
                    index++;

                    for (LocalTime time : times) {
                        LocalDateTime dateTime = now.plusDays(i).with(time);

                        Screening screening = new Screening();
                        screening.setScreeningTime(dateTime);
                        screening.setMovie(movie);
                        screening.setRoom(room);

                        screeningRepository.save(screening);
                    }
                }
            }
            index = 0;
        }
    }
}
