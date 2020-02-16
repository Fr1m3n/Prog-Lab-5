package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 15:11 09.02.2020

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.p3112.roman.exceptions.InvalidInputException;

@JsonAutoDetect
@JsonRootName("view")
public enum View implements Comparable<View> {
    STREET("Улица", 3),
    NORMAL("Обычный", 2),
    YARD("Двор", 1);

    private String rus;
    private int weight;


    View(String rus, int weight) {
        this.weight = weight;
        this.rus = rus;
    }

    public static View byOrdinal(int s) {
        for (View value : View.values()) {
            if (value.getWeight() == s) {
                return value;
            }
        }
        throw new InvalidInputException("Не найден вид, соответствующий строке: " + s);
    }

    public int getWeight() { return weight; }
    public String getRus() {
        return rus;
    }
}
