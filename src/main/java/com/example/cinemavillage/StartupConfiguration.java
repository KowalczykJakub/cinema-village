package com.example.cinemavillage;

import com.example.cinemavillage.model.Room;
import com.example.cinemavillage.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfiguration {

    private final RoomRepository roomRepository;

    public StartupConfiguration(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            Room room = JsonUtil.readJsonWithObjectMapper("src/main/resources/json/example-of-room.json");
            if (room.getRows() != null) {
                room.getRows().forEach(row -> {
                    row.setRoom(room);
                    if (row.getSeats() != null) {
                        row.getSeats().forEach(seat -> seat.setRow(row));
                    }
                });
            }
            roomRepository.save(room);
        };
    }
}
