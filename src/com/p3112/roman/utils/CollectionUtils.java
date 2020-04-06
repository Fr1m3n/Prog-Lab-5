package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 18:37 01.04.2020


import com.p3112.roman.collection.Coordinates;
import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.House;
import com.p3112.roman.collection.View;
import com.p3112.roman.exceptions.InvalidInputException;

import java.io.IOException;
import java.util.Arrays;

public class CollectionUtils {
    private CollectionUtils() {}

    /**
     * Метод, запрашивающий у пользователя ввод всех полей объекта Flat (с соответствующими сообщениями)
     *
     * @return Экземпляр Flat, созданный на основе введённых пользователем данных.
     */
    public static Flat readFlat(UserInterfaceImpl userInterface) throws ClassCastException, InvalidInputException, NumberFormatException, IOException {
        String name;
        do {
            name = userInterface.readWithMessage("Введите название квартиры: ", false);
        } while (name.isEmpty());
        Coordinates coordinates = readCoordinates(userInterface);
        long area = Long.parseLong(userInterface.readWithMessage("Введите площадь квартиры (целое число, больше 0): ", 0, -1));
        long numberOfRooms = Long.parseLong(userInterface.readWithMessage("Введите кол-во комнат в квартире (целое число от 0 до 18): ", 0, 18));
        Long livingSpace = Long.valueOf(userInterface.readWithMessage("Введите жилую площадь (целое число, больше 0): ", 0, -1));
        boolean isNew = parseBoolean(userInterface.readWithMessage("Квартира новая (+, -): ", false));
        View view = readView(userInterface);
        House house = readHouse(userInterface);
        return new Flat(name, coordinates, area, numberOfRooms, livingSpace, isNew, view, house);
    }


    /**
     * Метод считывающий дом.
     *
     * @return объект класса House, соответствующий вводу пользователя
     * @throws NumberFormatException в случае, если пользователь ввёл не число
     */
    public static House readHouse(UserInterfaceImpl userInterface) throws NumberFormatException, IOException {
        String houseName = userInterface.readWithMessage("Введите название дома: ", true);
        int houseYear = Integer.parseInt(userInterface.readWithMessage("Год построки дома: ", 0, -1));
        Long houseFloors = Long.parseLong(userInterface.readWithMessage("Количество этажей в доме: ", 0, -1));
        return new House(houseName, houseYear, houseFloors);
    }


    /**
     * Метод считывающий координаты.
     *
     * @return объект класса Coordinates, соответствующий вводу пользователя
     * @throws NumberFormatException в случае, если пользователь ввёл не число
     */
    public static Coordinates readCoordinates(UserInterfaceImpl userInterface) throws NumberFormatException, IOException {
        Float x = Float.parseFloat(userInterface.readWithMessage("Введите расположение квартиры по X (вещественное число): ", false));
        Long y = Long.valueOf(userInterface.readWithMessage("Теперь расположение по Y (целое число): ", false));
        return new Coordinates(x, y);
    }


    /**
     * Метод считывающий View с сообщениями для пользователя.
     *
     * @return View, соответствующий вводу пользователя
     */
    public static View readView(UserInterfaceImpl userInterface) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (View value : View.values()) {
            sb.append("\n").append(value.ordinal()).append(" - ").append(value.getRus());
        }
        String inp = userInterface.readWithMessage("Какой вид из квартиры. Введите число или пустую строку: " + sb.toString(), true);
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

    public static String writeHexBytes(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte b1 : b) {
            sb.append(Integer.toHexString(b1)).append(' ');
        }
        return sb.toString();
    }

    public static byte[] concatByteArrays(byte[] a, byte[] b) {
        byte[] c = Arrays.copyOf(a, a.length + b.length);
        for (int i = 0; i < b.length; i++) {
            c[a.length + i] = b[i];
        }
        return c;
    }

}
