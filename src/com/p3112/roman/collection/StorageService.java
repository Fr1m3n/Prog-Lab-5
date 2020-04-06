package com.p3112.roman.collection;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Интерфейс для сервиса, который будет отвечать за всю бизнес-логику необходимую для приложения.
 */
// Created by Roman Devyatilov (Fr1m3n) in 15:02 09.02.2020
public interface StorageService {
    String info();

    void add(Flat flat);

    boolean addIfMin(Flat flat);

    void clear();

    long countGreaterThanHouse(House house);

    List<Flat> filterLessThanNumberOfRooms(long numOfRooms);

    void insertAt(int ind, Flat flat);

    boolean removeAt(int ind);

    boolean removeById(long id);

    String show();

    boolean update(long id, Flat flat);

    int size();

    void save(String path) throws IOException;

    void load(Path path) throws IOException;
}
