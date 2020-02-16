package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 15:07 09.02.2020


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.NonNull;

/**
 * Модель координат
 */
@JsonAutoDetect
public class Coordinates {
    private Float x; //Поле не может быть null
    private Long y; //Поле не может быть null

    public Coordinates(Float x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    @Override
    public String toString() {
        return "x = " + x + ", y = " + y;
    }

    public Float getX() {
        return x;
    }

    public void setX(@NonNull Float x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(@NonNull Long y) {
        this.y = y;
    }
}
