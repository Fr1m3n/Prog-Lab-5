package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 15:11 09.02.2020


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.NonNull;

@JsonAutoDetect
public class House {
    private String name; //Поле может быть null
    private int year; //Значение поля должно быть больше 0
    @NonNull
    private Long numberOfFloors; //Поле не может быть null, Значение поля должно быть больше 0

    public House(String name, int year, Long numberOfFloors) {
        this.name = name;
        this.year = year;
        assert year > 0;
        this.numberOfFloors = numberOfFloors;
        assert numberOfFloors > 0;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", numberOfFloors=" + numberOfFloors +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(Long numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }
}
