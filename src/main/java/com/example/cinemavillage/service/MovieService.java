package com.example.cinemavillage.service;

import com.example.cinemavillage.model.Movie;
import com.example.cinemavillage.model.MovieData;
import com.example.cinemavillage.model.Room;
import com.example.cinemavillage.model.Screening;
import com.example.cinemavillage.repository.MovieRepository;
import com.example.cinemavillage.repository.RoomRepository;
import com.example.cinemavillage.repository.ScreeningRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class MovieService {
    private static final String TMDB_API_KEY = "90a76673077ab70a8064d60d22cbd875";
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String TMDB_API_KEY_PARAM = "?api_key=";
    private static final String TMDB_LANGUAGE_PARAM = "&language=en-US";
    private static final String TMDB_CREDITS_PATH = "/credits";
    private static final String NOW_PLAYING_PATH = "/now_playing";
    private static final int NUMBER_OF_DAYS = 5;

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
        List<Movie> savedMovies = getNowPlayingMovies(restTemplate);
        generateAndSaveScreenings(savedMovies, NUMBER_OF_DAYS);
    }

    private List<Movie> getNowPlayingMovies(RestTemplate restTemplate) {
        String url = TMDB_BASE_URL + NOW_PLAYING_PATH + TMDB_API_KEY_PARAM + TMDB_API_KEY + TMDB_LANGUAGE_PARAM;
        var moviesResponse = restTemplate.getForObject(url, LinkedHashMap.class);
        var movieResults = (List<LinkedHashMap>) moviesResponse.get("results");

        List<Movie> savedMovies = new ArrayList<>();
        for (var movieResult : movieResults) {
            MovieData movieData = getMovieData(restTemplate, movieResult.get("id"));
            Movie movie = mapMovieDataToMovie(movieData);
            savedMovies.add(movieRepository.save(movie));
        }
        return savedMovies;
    }

    private MovieData getMovieData(RestTemplate restTemplate, Object id) {
        String url = TMDB_BASE_URL + id + TMDB_API_KEY_PARAM + TMDB_API_KEY + TMDB_LANGUAGE_PARAM;
        MovieData movieData = restTemplate.getForObject(url, MovieData.class);
        String creditsUrl = TMDB_BASE_URL + id + TMDB_CREDITS_PATH + TMDB_API_KEY_PARAM + TMDB_API_KEY;
        LinkedHashMap credits = restTemplate.getForObject(creditsUrl, LinkedHashMap.class);
        movieData.setCrew((List<Map<String, Object>>) credits.get("crew"));
        return movieData;
    }

    private Movie mapMovieDataToMovie(MovieData movieData) {
        String director = movieData.getCrew().stream()
                .filter(member -> "Directing".equals(member.get("department")) && "Director".equals(member.get("job")))
                .map(member -> (String) member.get("name"))
                .findFirst()
                .orElse(null);

        Movie movie = new Movie();
        movie.setTitle(movieData.getTitle());
        movie.setOverview(movieData.getOverview());
        movie.setReleaseDate(LocalDate.parse(movieData.getRelease_date()));
        movie.setRuntime(movieData.getRuntime());
        movie.setDirector(director);
        movie.setPosterPath(TMDB_IMAGE_BASE_URL + movieData.getPoster_path());

        return movie;
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
