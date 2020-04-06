package com.p3112.roman.network;
// Created by Roman Devyatilov (Fr1m3n) in 14:27 04.04.2020


import com.p3112.roman.utils.CollectionUtils;

import java.util.Arrays;

/**
 * Класс, хранящий в себе константные значения системных команд.
 * Системный вызов определяется по первому байту, который должен быть равен значению SYSCMD
 */
public class SystemCommand {
    public static final byte SYSCMD = 0x7f;

    public static final byte[] IS_READY = {SYSCMD, 0x3, 0x2, 0x0};
    public static final byte[] CLOSE_SESSION = {SYSCMD, 0x1, 0x0, 0x0};

    public static final byte[] READY = {SYSCMD, 0x3, 0x2, 0x1};
    public static final byte[] SESSION_CLOSED = {SYSCMD, 0x1, 0x0, 0x1};
    public static final byte[] FILE_REQUEST = {SYSCMD, 0xf, 0xa, 0x0};

    /**
     * Проверяет является ли массив системной командой
     * @param command потенциальная системная команда
     * @return true - если первый байт равен SYSCMD, false - иначе или если длина масива < 1
     */
    public static boolean isServiceCommand(byte[] command) {
        if (command.length < 1) {
            return false;
        }
        return command[0] == SYSCMD;
    }

    /**
     * Проверяетс 2 массива байт на равенство
     * @param a массив для сравнения
     * @param b массив для сравнения
     * @return true - если массивы равны
     */
    public static boolean equals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }


    /**
     * Формирует системную команду запроса содержимого файла
     * @param fileName Имя файла
     * @return системная команда, содержащая имя файла
     */
    public static byte[] getFileRequestCommand(String fileName) {
        byte fileNameLength = (byte)fileName.getBytes().length;
        byte[] res = CollectionUtils.concatByteArrays(FILE_REQUEST, fileName.getBytes());
        res[3] = fileNameLength;
        return res;
    }

    /**
     * Достаёт из системной команды запроса содержимого имя файла.
     * @param b команда
     * @return имя файла из запроса
     */
    public static String parseFileRequestCommand(byte[] b) {
        int fileNameLength = b[3];
        System.out.println(fileNameLength);
        if (b.length < fileNameLength + FILE_REQUEST.length) {
            return null; // TODO: Invalid filename Exception
        }
        return new String(Arrays.copyOfRange(b, FILE_REQUEST.length, b.length));
    }
    // для тестов
    public static void main(String[] args) {
        byte[] b = {SYSCMD};
        System.out.println(new String(b));
    }
}
