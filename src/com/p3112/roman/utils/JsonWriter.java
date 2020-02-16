package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 17:22 09.02.2020


import com.fasterxml.jackson.databind.ObjectMapper;
import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.FlatDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Класс, отвечающий за сереализацию FlatDTO в JSON
 */
public class JsonWriter {
    private ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Записывает сереализованный массив из FlatDTO в файл в формате JSON.
     * @param storageDTO массив для сереализации
     * @param pathToFile путь до файла, в который будет сохранён массив
     * @throws IOException В случае если файл недоступен для чтения.
     */
    public void writeCollectionToFile(FlatDTO[] storageDTO, String pathToFile) throws IOException {
        File file = new File(pathToFile);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(storageDTO).getBytes());
    }
}
