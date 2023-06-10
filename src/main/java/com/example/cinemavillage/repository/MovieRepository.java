package com.example.cinemavillage.repository;

import com.example.cinemavillage.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
