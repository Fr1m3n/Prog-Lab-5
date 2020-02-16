package com.p3112.roman.collection;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Интерфейс для коллекции.
 *
 * @param <T> Тип элеметов коллекции
 */
// Writed by Roman Devyatilov (Fr1m3n) in 9:42 07.02.2020
public interface Storage<T> {
    void put(T obj);

    void put(int index, T obj);

    T get(int index);

    T get();

    void remove(T obj);

    int size();

    void clear();

    List<T> toList();

    Date getInitializationTime();

    Class<?> getCollectionClass();

    void setCollection(Collection<T> collection);

    long getMaximumId();

//    void loadFromFile();
}
