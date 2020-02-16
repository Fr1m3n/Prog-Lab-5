package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 17:05 09.02.2020

import com.fasterxml.jackson.databind.ObjectMapper;
import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.FlatDTO;

import java.io.*;

/**
 * Класс, отвечающий за десереализацию квартир из JSON
 */
public class JsonReader {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Читает список DTO для квартир.
     * @param pathToFile путь до файла с JSON
     * @return Массив FlatDTO, объектов из файла
     * @throws IOException В случае если файл недоступен для чтения.
     */
    public FlatDTO[] readCollectionFromFile(String pathToFile) throws IOException {
        try {
            FlatDTO[] flats = objectMapper.readValue(new FileReader(pathToFile), FlatDTO[].class);
            return flats;
        } catch (ClassCastException e) {
            System.out.println("Проблема при касте какая-то хз, сам глянь чё там...");
            return null;
        }
    }

}
