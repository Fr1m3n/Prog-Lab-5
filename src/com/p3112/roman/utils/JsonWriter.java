package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 17:22 09.02.2020


import com.fasterxml.jackson.databind.ObjectMapper;
import com.p3112.roman.collection.Flat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class JsonWriter {
    private ObjectMapper objectMapper = new ObjectMapper();

    public void writeFlatToFile(Flat flat, String pathToFile) throws IOException {
        File file = new File(pathToFile);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(objectMapper.writeValueAsString(flat).getBytes());
    }

    public void writeCollectionToFile(List<Flat> flats, String pathToFile) throws IOException {
        File file = new File(pathToFile);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(objectMapper.writeValueAsString(flats).getBytes());
    }
}