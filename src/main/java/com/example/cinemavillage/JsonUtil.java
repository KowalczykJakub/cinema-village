package com.example.cinemavillage;

import com.example.cinemavillage.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Room readJsonWithObjectMapper(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), Room.class);
    }
}
