package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 15:03 08.02.2020


import java.util.Collection;

public class StackFlatStorageService implements StorageService {
    private static Storage<Flat> st;

    public StackFlatStorageService(Storage<Flat> st) {
        if(StackFlatStorageService.st == null) {
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
}
