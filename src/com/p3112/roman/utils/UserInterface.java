package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 16:12 15.02.2020


import com.p3112.roman.collection.Coordinates;
import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.House;
import com.p3112.roman.collection.View;
import com.p3112.roman.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

/**
 * Класс, отвечающий за обмен информацией с пользователем.
 */
@Slf4j
public class UserInterface {
    private Reader reader;
    private Writer writer;
    private Scanner scanner;
    private boolean interactive;

    /**
     * Конструктор, который создаёт интерфейс и определяет куда писать и откуда считывать.
     *
     * @param reader      Откуда считывать
     * @param writer      Куда писать
     * @param interactive Флаг, обозначающий режим работы интерфейса (true - интерактивный)
     */
    public UserInterface(Reader reader, Writer writer, boolean interactive) {
        this.reader = reader;
        this.writer = writer;
        this.interactive = interactive;
        this.scanner = new Scanner(reader);
    }

    /**
     * Метод, запрашивающий ввод из стандартного потока ввода. Перед вводом выводит сообщение в стандартный поток вывода.
     *
     * @param message  Сообщение для пользователя, будет выведено перед вводом
     * @param nullable Флаг. True - если мы допускаем пустой ввод от пользователя. False - если нам надо добиться от него не пустого ввода.
     * @return Введённую строку пользователя, или null если пользователь ввёл пустую строку и флаг nullable равен true
     */
    public String readWithMessage(String message, boolean nullable) {
        String result = "";
        do {
            if (result == null) {
                writeln("Введите не пустую строку.");
            }
            if (interactive) {
                writeln(message);
            }
            result = scanner.nextLine();
            result = result.isEmpty() ? null : result;
        } while (interactive && !nullable && result == null);
        if (!interactive && result == null) {
            throw new InvalidInputException("Получена пуста строка в поле, которое не может быть null.");
        }
        return result;
    }

    /**
     * Выводит в поток вывода строку с добавлением символа переноса.
     * @param message строка для вывода.
     */
    public void writeln(String message) {
        write(message + "\n");
    }


    /**
     * Выводит в поток вывода строку.
     *
     * @param message строка для вывода.
     */
    public void write(String message) {
        try {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            log.error("Ошибка при выводе. {}", e.getMessage());
        }
    }

    /**
     * Считывает из потока число и проверяет его на вхождение в промежуток [min; max]. При не корректном вводе, запрашивается повторный ввод.
     *
     * @param message сообщение, пользователю
     * @param min     нижняя граница (-1, если неважна)
     * @param max     верхняя граница (-1, если неважна)
     * @return Введённое пользователем число.
     */
    public String readWithMessage(String message, int min, int max) {
        String res;
        do {
            res = readWithMessage(message, false);
        } while (!checkNumber(Double.parseDouble(res), min, max));
        return res;
    }

    public String read() {
        return scanner.nextLine();
    }

    /**
     * Метод, запрашивающий у пользователя ввод всех полей объекта Flat (с соответствующими сообщениями)
     *
     * @return Экземпляр Flat, созданный на основе введённых пользователем данных.
     */
    public Flat readFlat() throws ClassCastException, InvalidInputException, NumberFormatException {
        String name;
        do {
            name = readWithMessage("Введите название квартиры: ", false);
        } while (name.isEmpty());
        Coordinates coordinates = readCoordinates();
        long area = Long.parseLong(readWithMessage("Введите площадь квартиры (целое число, больше 0): ", 0, -1));
        long numberOfRooms = Long.parseLong(readWithMessage("Введите кол-во комнат в квартире (целое число от 0 до 18): ", 0, 18));
        Long livingSpace = Long.valueOf(readWithMessage("Введите жилую площадь (целое число, больше 0): ", 0, -1));
        boolean isNew = parseBoolean(readWithMessage("Квартира новая (+, -): ", false));
        View view = readView();
        House house = readHouse();
        return new Flat(name, coordinates, area, numberOfRooms, livingSpace, isNew, view, house);
    }


    /**
     * Метод считывающий дом.
     *
     * @return объект класса House, соответствующий вводу пользователя
     * @throws NumberFormatException в случае, если пользователь ввёл не число
     */
    public House readHouse() throws NumberFormatException {
        String houseName = readWithMessage("Введите название дома: ", true);
        int houseYear = Integer.parseInt(readWithMessage("Год построки дома: ", 0, -1));
        Long houseFloors = Long.parseLong(readWithMessage("Количество этажей в доме: ", 0, -1));
        return new House(houseName, houseYear, houseFloors);
    }


    /**
     * Метод считывающий координаты.
     *
     * @return объект класса Coordinates, соответствующий вводу пользователя
     * @throws NumberFormatException в случае, если пользователь ввёл не число
     */
    public Coordinates readCoordinates() throws NumberFormatException {
        Float x = Float.parseFloat(readWithMessage("Введите расположение квартиры по X (вещественное число): ", false));
        Long y = Long.valueOf(readWithMessage("Теперь расположение по Y (целое число): ", false));
        return new Coordinates(x, y);
    }


    /**
     * Метод считывающий View с сообщениями для пользователя.
     *
     * @return View, соответствующий вводу пользователя
     */
    public View readView() {
        StringBuilder sb = new StringBuilder();
        for (View value : View.values()) {
            sb.append("\n").append(value.ordinal()).append(" - ").append(value.getRus());
        }
        String inp = readWithMessage("Какой вид из квартиры. Введите число или пустую строку: " + sb.toString(), true);
        if (inp == null) {
            return null;
        }
        return View.byOrdinal(Integer.parseInt(inp));
    }


    /**
     * Метод, который парсит логическое значение.
     *
     * @param s входная строка для парсинга
     * @return true - если s == "+", иначе false
     */
    public static boolean parseBoolean(String s) {
        if ("+".equals(s.toLowerCase())) {
            return true;
        } else if ("-".equals(s.toLowerCase())) {
            return false;
        }
        return false;
    }

    /**
     * Метод, проверяющий число на вхождение в заданный отрезок.
     * Для неопределённой границы (например, нас интересует ТОЛЬКО минимальная граница) следует ввести отрицательное число
     *
     * @param s   Число для проверки
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return true - если значение числа лежит в отрезке (min, max], иначе false
     */
    public static boolean checkNumber(double s, int min, int max) {
        return ((min < 0 || Math.abs(s - min) < 10e-9 || s > min) && (max < 0 || s <= max));
    }


    /**
     * Метод возрващающий есть ли что считывать из входного потока.
     *
     * @return Есть ли ещё что считывать.
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }
}
