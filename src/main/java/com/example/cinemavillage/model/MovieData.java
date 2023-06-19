package com.example.cinemavillage.model;

import java.util.List;
import java.util.Map;

public class MovieData {
    Integer id;
    String title;
    String overview;
    String release_date;
    Integer runtime;
    String poster_path;
    List<Map<String, Object>> crew;

    public MovieData() {
    }

    public MovieData(Integer id, String title, String overview, String release_date, Integer runtime, String poster_path, List<Map<String, Object>> crew) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.runtime = runtime;
        this.poster_path = poster_path;
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<Map<String, Object>> getCrew() {
        return crew;
    }

    public void setCrew(List<Map<String, Object>> crew) {
        this.crew = crew;
    }
}
