package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 15:11 09.02.2020

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.p3112.roman.exceptions.InvalidInputException;

@JsonAutoDetect
@JsonRootName("view")
public enum View implements Comparable<View> {
    STREET("Улица"),
    NORMAL("Обычный"),
    YARD("Двор");

    private String rus;


    View(String rus) {
        this.rus = rus;
    }

    public static View byOrdinal(int s) {
        for (View value : View.values()) {
            if (value.ordinal() == s) {
                return value;
            }
        }
        throw new InvalidInputException("Не найден вид, соответствующий строке: " + s);
    }

    public String getRus() {
        return rus;
    }
}
