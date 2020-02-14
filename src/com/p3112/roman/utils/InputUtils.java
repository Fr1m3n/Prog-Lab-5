package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 17:37 09.02.2020


import com.p3112.roman.collection.Coordinates;
import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.House;
import com.p3112.roman.collection.View;
import com.p3112.roman.exceptions.InvalidInputException;

import java.util.Scanner;

public class InputUtils {

    public static void checkNull(Object obj) {
        if (obj == null) {
            throw new InvalidInputException("NonNull field has got null value");
        }
    }


    /**
     * Метод, запрашивающий ввод из стандартного потока ввода. Перед вводом выводит сообщение в стандартный поток вывода.
     *
     * @param message  Сообщение для пользователя, будет выведено перед вводом
     * @param nullable Флаг. True - если мы допускаем пустой ввод от пользователя. False - если нам надо добиться от него не пустого ввода.
     * @return Введённую строку пользователя, или null если пользователь ввёл пустую строку и флаг nullable равен true
     */
    public static String readWithMessage(String message, boolean nullable) {
        boolean flag = true;
        String result = null;
        do {
            System.out.print(message);
            Scanner scanner = new Scanner(System.in);
//            while (!scanner.hasNextLine());
            result = scanner.nextLine();
            result = result.isEmpty() ? null : result;
        } while (!nullable && result == null);
        return result;
    }

    public static String readWithMessage(String message, int min, int max) {
        String res;
        do {
            res = readWithMessage(message, false);
        } while (!checkStringLength(Double.parseDouble(res), min, max));
        return res;
    }


    /**
     * Метод, запрашивающий у пользователя ввод всех полей объекта Flat (с соответствующими сообщениями)
     *
     * @return Экземпляр Flat, созданный на основе введённых пользователем данных.
     */
    public static Flat readFlatFromConsole() {
        boolean flag = false;
        String name = readWithMessage("Введите название квартиры: ", false);
        Coordinates coordinates = readCoordinatesFromConsole();
        long area = Long.parseLong(readWithMessage("Введите площадь квартиры (целое число): ", 0, -1));
        long numberOfRooms = Long.parseLong(readWithMessage("Введите кол-во комнат в квартире (целое число от 0 до 18): ", 0, 18));
        Long livingSpace = Long.valueOf(readWithMessage("Введите жилую площадь (целое число): ", 0, -1));
        boolean isNew = Boolean.parseBoolean(readWithMessage("Квартира новая (да, нет): ", false));
        View view = View.fromString(readWithMessage("Какой вид из квартиры (обычный, улица, двор): ", true));
        House house = readHouseFromConsole();
        return new Flat(name, coordinates, area, numberOfRooms, livingSpace, isNew, view, house);
    }

    public static House readHouseFromConsole() {
        String houseName = readWithMessage("Введите название дома: ", true);
        int houseYear = Integer.parseInt(readWithMessage("Год построки дома: ", 0, -1));
        Long houseFloors = Long.parseLong(readWithMessage("Количество этажей в доме: ", 0, -1));
        return new House(houseName, houseYear, houseFloors);
    }

    public static Coordinates readCoordinatesFromConsole() {
        Float x = Float.parseFloat(readWithMessage("Введите расположение квартиры по X (вещественное число): ", false));
        Long y = Long.valueOf(readWithMessage("Теперь расположение по Y (целое число): ", false));
        return new Coordinates(x, y);
    }

    public static boolean parseBoolean(String s) {
        if ("да".equals(s.toLowerCase())) {
            return true;
        } else if ("нет".equals(s.toLowerCase())) {
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
     * @return true - если значение числа лежит в отрезке [min, max], иначе false
     */
    private static boolean checkStringLength(double s, int min, int max) {
        return ((min < 0 || s >= min) && (max < 0 || s <= max));
    }

}
