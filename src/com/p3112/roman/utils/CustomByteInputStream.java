package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 0:45 02.04.2020


import java.io.IOException;
import java.io.InputStream;

public class CustomByteInputStream extends InputStream {

    private byte[] buffer;
    private int pos;
    private int count;

    public CustomByteInputStream(byte [] buf) {
        this.buffer = buf;
        this.pos = 0;
        this.count = buf.length;
    }

    @Override
    public int read() throws IOException {
        return ((pos < count) ? buffer[pos++] : -1);
    }
}
