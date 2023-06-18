package com.example.cinemavillage.model;

import java.time.LocalDate;
import java.util.List;

public class MovieInfoDto {
    private Long id;
    private String title;
    private String director;
    private String overview;
    private LocalDate releaseDate;
    private Integer runtime;
    private String posterPath;
    private List<ScreeningInfoDto> screenings;

    public MovieInfoDto() {
    }

    public MovieInfoDto(Long id, String title, String director, String overview, LocalDate releaseDate, Integer runtime, String posterPath, List<ScreeningInfoDto> screenings) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.posterPath = posterPath;
        this.screenings = screenings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ScreeningInfoDto> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<ScreeningInfoDto> screenings) {
        this.screenings = screenings;
    }
}
