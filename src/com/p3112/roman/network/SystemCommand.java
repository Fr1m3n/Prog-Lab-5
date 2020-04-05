package com.p3112.roman.network;
// Created by Roman Devyatilov (Fr1m3n) in 14:27 04.04.2020


import com.p3112.roman.utils.CollectionUtils;

/**
 * Класс, хранящий в себе константные значения системных команд.
 * Системный вызов определяется по первому байту, который должен быть равен значению SYSCMD
 */
public class SystemCommand {
    public static final byte SYSCMD = 0xf;
    public static final int SYSCMD_SIZE = 4;

    public static final byte[] IS_READY = {SYSCMD, 0x3, 0x2, 0x0};
    public static final byte[] CLOSE_SESSION = {SYSCMD, 0x1, 0x0, 0x0};

    public static final byte[] READY = {SYSCMD, 0x3, 0x2, 0x1};

    /**
     * Проверяет является ли массив системной командой
     * @param command потенциальная системная команда
     * @return true - если первый байт равен SYSCMD, false - иначе или если длина масива не равна SYSCMD_SIZE
     */
    public static boolean isServiceCommand(byte[] command) {
        if (command.length != SYSCMD_SIZE) {
            return false;
        }
        return command[0] == SYSCMD;
    }

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
//    для тестов
//    public static void main(String[] args) {
//        String q = new String(IS_READY);
//        System.out.println(CollectionUtils.writeHexBytes(q.getBytes()));
//    }
}
