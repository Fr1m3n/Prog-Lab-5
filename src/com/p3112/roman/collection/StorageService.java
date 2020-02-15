package com.p3112.roman.collection;

import java.util.Collection;
import java.util.List;

// Created by Roman Devyatilov (Fr1m3n) in 15:02 09.02.2020
public interface StorageService {
    String info();

    void add(Flat flat);

    void add(Collection<Flat> flats);

    void addIfMin(Flat flat);

    void clear();

    void countGreaterThanHouse(House house);

    List<Object> filterLessThanNumberOfRooms(long numOfRooms);

    void insertAt(int ind, Flat flat);

    void removeAt(int ind);

    void removeById(long id);

    String show();

    void update(long id, Flat flat);

    int size();

    void save(String path);
}
