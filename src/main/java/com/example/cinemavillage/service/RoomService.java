package com.example.cinemavillage.service;

import com.example.cinemavillage.model.Movie;
import com.example.cinemavillage.model.Room;
import com.example.cinemavillage.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }
}
