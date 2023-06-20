package com.example.cinemavillage;

import com.example.cinemavillage.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtil {
    public static Room readJsonWithObjectMapper(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new InputStreamReader(JsonUtil.class.getResourceAsStream(path)), Room.class);
    }
}
