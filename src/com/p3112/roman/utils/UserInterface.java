package com.p3112.roman.utils;

import java.io.IOException;

// Created by Roman Devyatilov (Fr1m3n) in 17:42 01.04.2020
public interface UserInterface {
    void write(String message) throws IOException;

    default void write(byte[] data) throws IOException {
        write(new String(data));
    }

    String read() throws IOException;
}
