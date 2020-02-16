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
    public boolean addIfMin(Flat flat) {
        List<Flat> flats = st.toList().stream().sorted().collect(Collectors.toList());
        if (flats.isEmpty()) {
            return false;
        }
        Flat firstFlat = flats.get(0);
        if (firstFlat.compareTo(flat) < 0) {
            add(flat);
            log.info("Элемент {} добавлен успешно", flat);
            return true;
        } else {
            log.info("Элемент оказался больше минимального.");
            return false;
        }
    }

    @Override
    public void clear() {
        st.clear();
    }

    @Override
    public long countGreaterThanHouse(House house) {
        return st.toList().stream().filter(x -> x.getHouse().compareTo(house) < 0).count();

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
    public boolean removeAt(int ind) {
        if (st.size() <= ind) {
            return false;
        }
        st.remove(st.get(ind));
        return true;
    }

    @Override
    public boolean removeById(long id) {
        List<Flat> flatsToDelete = st.toList().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if (flatsToDelete.isEmpty()) {
            return false;
        }
        flatsToDelete.forEach(st::remove);
        return true;
    }

    @Override
    public String show() {
        StringBuilder sb = new StringBuilder("Элементов в коллекции: " + st.size()).append("\n");
        st.toList().forEach(x -> sb.append(x.toString()).append('\n'));
        return sb.toString();
    }

    @Override
    public boolean update(long id, Flat flat) {
        if (!removeById(id)) {
            return false;
        }
        flat.setId(id);
        st.put(flat);
        return true;
    }

    @Override
    public int size() {
        return st.size();
    }

    @Override
    public void save(String pathToFile) throws IOException {
        JsonWriter jsonWriter = new JsonWriter();
        try {
            FlatDTO[] flats = new FlatDTO[st.size()];
            st.toList().stream().map(FlatDTO::new).collect(Collectors.toList()).toArray(flats);
            jsonWriter.writeCollectionToFile(flats, pathToFile);
        } catch (IOException e) {
            log.error("Ошибка во время сохранения коллекции в файл.");
            throw e;
        }
    }
}
