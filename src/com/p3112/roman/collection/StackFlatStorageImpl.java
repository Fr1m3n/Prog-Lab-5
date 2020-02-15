package com.p3112.roman.collection;
// Writed by Roman Devyatilov (Fr1m3n) in 9:14 07.02.2020


import java.util.*;

public class StackFlatStorageImpl implements Storage<Flat> {
    private Stack<Flat> flats = new Stack<>();
    private Date creationDate;
    private long maxId = 0;

    public StackFlatStorageImpl() {
        creationDate = new Date();
    }


    public Stack<Flat> getFlats() {
        return flats;
    }

    public void setFlats(Stack<Flat> flats) {
        this.flats = flats;
    }

    @Override
    public void put(Flat obj) {
        obj.setId(maxId);
        flats.push(obj);
        maxId = Math.max(maxId, obj.getId() + 1);
    }

    @Override
    public void put(int index, Flat obj) {
        obj.setId(maxId);
        flats.add(index, obj);
        maxId = Math.max(maxId, obj.getId() + 1);
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
    public void remove(Flat obj) {
        flats.remove(obj);
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
    public List<Flat> toList() {
        return flats;
    }

    @Override
    public Date getInitializationTime() {
        return creationDate;
    }

    @Override
    public Class<?> getCollectionClass() {
        return flats.getClass();
    }

    @Override
    public void setCollection(Collection<Flat> collection) {
        flats = (Stack<Flat>) collection;
    }

    @Override
    public long getMaximumId() {
        return maxId;
    }


}
