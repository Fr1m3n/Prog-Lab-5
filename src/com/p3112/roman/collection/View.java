package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 15:11 09.02.2020

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public enum View {
    STREET,
    YARD,
    NORMAL;
}
