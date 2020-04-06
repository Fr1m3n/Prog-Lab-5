package com.p3112.roman.network;
// Created by Roman Devyatilov (Fr1m3n) in 23:19 29.03.2020


import java.io.Serializable;

/**
 * Класс "обёртка" для всех сообщений между клиентом и сервером
 */
public class Request implements Serializable {

    private byte[] content;
    private int size;
    private boolean systemCommand;

    public Request(byte[] content, boolean system) {
        this.content = content;
        this.systemCommand = system;
        this.size = content.length;
    }

    public Request(byte[] content) {
        this(content, false);
    }

    public Request(String content) {
        this.content = content.getBytes();
        this.size = this.content.length;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentByString() {
        return new String(content);
    }

    public int getSize() {
        return size;
    }

    public boolean isSystemCommand() {
        return systemCommand;
    }
}
