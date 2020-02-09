package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 15:07 09.02.2020


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.NonNull;

@JsonAutoDetect
public class Coordinates {
    @NonNull
    private Float x; //Поле не может быть null
    @NonNull
    private Long y; //Поле не может быть null

    public Coordinates(@NonNull Float x, @NonNull Long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }
}
