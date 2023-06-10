package com.example.cinemavillage.repository;

import com.example.cinemavillage.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}
