package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 17:40 01.04.2020


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Scanner;

@Slf4j
public class UserInterfaceCLIImpl extends UserInterfaceImpl {
    private Reader reader;
    private Writer writer;
    private Scanner scanner;

    /**
     * Конструктор, который создаёт интерфейс и определяет куда писать и откуда считывать.
     *
     * @param inputStream      Откуда считывать
     * @param outputStream      Куда писать
     * @param interactive Флаг, обозначающий режим работы интерфейса (true - интерактивный)
     */
    public UserInterfaceCLIImpl(InputStream inputStream, OutputStream outputStream, boolean interactive) {
        super(inputStream, outputStream, interactive);
        this.reader = new InputStreamReader(inputStream);
        this.writer = new OutputStreamWriter(outputStream);
        this.scanner = new Scanner(inputStream);
    }


    /**
     * Выводит в поток вывода строку.
     *
     * @param message строка для вывода.
     */
    @Override
    public void write(String message) {
        try {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            log.error("Ошибка при выводе. {}", e.getMessage());
        }
    }


    @Override
    public String read() {
        return scanner.nextLine();
    }
}
