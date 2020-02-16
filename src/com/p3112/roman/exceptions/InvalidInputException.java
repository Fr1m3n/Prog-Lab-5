package com.p3112.roman.exceptions;
// Created by Roman Devyatilov (Fr1m3n) in 17:35 09.02.2020


/**
 * Исключение, обозначающее не верный ввод.
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
