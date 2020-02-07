package com.p3112.roman;

// Writed by Roman Devyatilov (Fr1m3n) in 9:42 07.02.2020
public interface Storage<T> {
    void put(T obj);
    void put(int index, T obj);
    T get(int index);
    T get();
    int size();
    void clear();
    String info();
}
