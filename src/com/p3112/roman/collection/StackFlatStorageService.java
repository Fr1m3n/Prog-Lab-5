package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 15:03 08.02.2020


import com.p3112.roman.utils.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class StackFlatStorageService implements StorageService {
    private static Storage<Flat> st;

    public StackFlatStorageService(Storage<Flat> st) {
        if (StackFlatStorageService.st == null) {
            StackFlatStorageService.st = st;
        }
    }

    public String info() {
        StringBuilder sb = new StringBuilder();
        sb.append("Время инициализации коллекции: ").append(st.getInitializationTime().toString()).append('\n');
        sb.append("Количество элементов в коллекции: ").append(st.size()).append('\n');
        sb.append("Тип коллекции: ").append(st.getCollectionClass().getName());
        return sb.toString();
    }

    @Override
    public void add(Flat flat) {
        st.put(flat);
    }

    @Override
    public void add(Collection<Flat> flats) {
        for (Flat flat : flats) {
            st.put(flat);
        }
    }

    @Override
    public void addIfMin(Flat flat) {
        st.toList().stream().sorted().findFirst().ifPresent(x -> {
            add(x);
            log.info("Элемент {} добавлен успешно", x);
        });
    }

    @Override
    public void clear() {
        st.clear();
    }

    @Override
    public void countGreaterThanHouse(House house) {

    }

    @Override
    public List<Object> filterLessThanNumberOfRooms(long numOfRooms) {
        return st.toList().stream().filter(x -> x.getNumberOfRooms() < numOfRooms).collect(Collectors.toList());

    }

    @Override
    public void insertAt(int ind, Flat flat) {
        st.put(ind, flat);
    }

    @Override
    public void removeAt(int ind) {
        st.remove(st.get(ind));
    }

    @Override
    public void removeById(long id) {
        List<Flat> flatsToDelete = st.toList().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        flatsToDelete.forEach(st::remove);
    }

    @Override
    public String show() {
        StringBuilder sb = new StringBuilder("Элементов в коллекции: " + st.size()).append("\n");
        st.toList().forEach(x -> {
            sb.append(x.toString()).append('\n');
        });

        return sb.toString();
    }

    @Override
    public void update(long id, Flat flat) {
        removeById(id);
        flat.setId(id);
        st.put(flat);
    }

    @Override
    public int size() {
        return st.size();
    }

    @Override
    public void save(String pathToFile) {
        JsonWriter jsonWriter = new JsonWriter();
        try {
            FlatDTO[] flats = new FlatDTO[st.size()];
            st.toList().stream().map(FlatDTO::new).collect(Collectors.toList()).toArray(flats);
            jsonWriter.writeCollectionToFile(flats, pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
