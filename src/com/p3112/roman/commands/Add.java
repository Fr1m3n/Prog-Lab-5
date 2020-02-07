package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:55 07.02.2020

public class Add extends AbstractCommand {
    public Add() {
        command = "add";
        helpText = "Добавить новый элемент в коллекцию. Принимает один аргумент.";
    }

    @Override
    public void execute(String[] args) {

    }
}
