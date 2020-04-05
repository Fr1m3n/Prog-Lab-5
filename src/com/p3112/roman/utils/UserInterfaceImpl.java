package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 16:12 15.02.2020

import com.p3112.roman.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Класс, отвечающий за обмен информацией с пользователем.
 */
@Slf4j
public abstract class UserInterfaceImpl implements UserInterface {
    protected InputStream is;
    protected OutputStream os;
    protected boolean interactive;

    /**
     * Конструктор, который создаёт интерфейс и определяет куда писать и откуда считывать.
     *
     * @param reader      Откуда считывать
     * @param writer      Куда писать
     * @param interactive Флаг, обозначающий режим работы интерфейса (true - интерактивный)
     */
    public UserInterfaceImpl(InputStream reader, OutputStream writer, boolean interactive) {
        this.is = reader;
        this.os = writer;
        this.interactive = interactive;
    }



    /**
     * Выводит в поток вывода строку с добавлением символа переноса.
     * @param message строка для вывода.
     */
    public void writeln(String message) {
        try {
            write(message + "\n");
        } catch (IOException e) {
            log.error("Ошибка ввода/вывода...");
        }
    }


    /**
     * Метод, запрашивающий ввод из стандартного потока ввода. Перед вводом выводит сообщение в стандартный поток вывода.
     *
     * @param message  Сообщение для пользователя, будет выведено перед вводом
     * @param nullable Флаг. True - если мы допускаем пустой ввод от пользователя. False - если нам надо добиться от него не пустого ввода.
     * @return Введённую строку пользователя, или null если пользователь ввёл пустую строку и флаг nullable равен true
     */
    public String readWithMessage(String message, boolean nullable) throws IOException {
        String result = "";
        StringBuilder msgToUser = new StringBuilder();
        do {
            if (result == null) {
                msgToUser.append("Введите не пустую строку.").append('\n');
            }
            if (interactive) {
                msgToUser.append(message);
            }
            writeln(msgToUser.toString());
            result = read();
            result = result.isEmpty() ? null : result;
        } while (interactive && !nullable && result == null);
        if (!interactive && result == null) {
            throw new InvalidInputException("Получена пуста строка в поле, которое не может быть null.");
        }
        return result;
    }


    /**
     * Считывает из потока число и проверяет его на вхождение в промежуток [min; max]. При не корректном вводе, запрашивается повторный ввод.
     *
     * @param message сообщение, пользователю
     * @param min     нижняя граница (-1, если неважна)
     * @param max     верхняя граница (-1, если неважна)
     * @return Введённое пользователем число.
     */
    public String readWithMessage(String message, int min, int max) throws IOException {
        String res;
        do {
            res = readWithMessage(message, false);
        } while (!CollectionUtils.checkNumber(Double.parseDouble(res), min, max));
        return res;
    }

    @Override
    public abstract void write(String message) throws IOException;

    @Override
    public abstract String read() throws IOException;

    /**
     * Метод возрващающий есть ли что считывать из входного потока.
     *
     * @return Есть ли ещё что считывать.
     */
    public boolean hasNextLine() {
        return false;
    }
}
