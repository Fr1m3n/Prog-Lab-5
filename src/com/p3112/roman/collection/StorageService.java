package com.p3112.roman.collection;

import java.util.Collection;

// Created by Roman Devyatilov (Fr1m3n) in 15:02 09.02.2020
public interface StorageService {
    String info();
    void add(Flat flat);
    void add(Collection<Flat> flats);
}
