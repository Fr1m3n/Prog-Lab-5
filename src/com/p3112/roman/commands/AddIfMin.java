package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:58 07.02.2020

import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public class AddIfMin extends AbstractCommand {
    public AddIfMin() {
        command = "add_if_min";
        helpText = "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции.";
    }

    @Override
    public void execute(Storage<Flat> storage, String[] args) {

    }
}
