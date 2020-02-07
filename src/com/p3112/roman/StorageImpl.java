package com.p3112.roman;
// Writed by Roman Devyatilov (Fr1m3n) in 9:14 07.02.2020


import java.util.Date;
import java.util.Stack;

public class StorageImpl implements Storage<Flat> {
    private Stack<Flat> flats = new Stack<>();
    private Date creationDate;
    private String storageType = "Stack";

    private StorageImpl() {
        creationDate = new Date();
    }

    @Override
    public void put(Flat obj) {
        flats.push(obj);
    }

    @Override
    public void put(int index, Flat obj) {

    }

    @Override
    public Flat get(int index) {
        return flats.get(index);
    }

    @Override
    public Flat get() {
        return flats.peek();
    }

    @Override
    public int size() {
        return flats.size();
    }

    @Override
    public void clear() {
        flats.clear();
    }

    @Override
    public String info() {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }
}
