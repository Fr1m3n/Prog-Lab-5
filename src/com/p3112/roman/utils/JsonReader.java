package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 17:05 09.02.2020

import com.fasterxml.jackson.databind.ObjectMapper;
import com.p3112.roman.collection.Flat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class JsonReader {
    private ObjectMapper objectMapper = new ObjectMapper();

    public Flat readFlatFromFile(String pathToFile) throws IOException {
        return (Flat) objectMapper.readValue(new FileInputStream(pathToFile), Flat.class);
    }

}
