package com.example.cinemavillage.repository;

import com.example.cinemavillage.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @Query("SELECT s FROM Screening s WHERE s.screeningTime BETWEEN :startOfDay AND :endOfDay")
    List<Screening> findScreeningsByDay(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
