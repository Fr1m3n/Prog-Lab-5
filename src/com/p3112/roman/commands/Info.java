package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:40 07.02.2020


import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public class Info extends AbstractCommand {
    public Info() {
        command = "info";
        helpText = "Выводит в стандартный поток информацию о коллекции.";
    }

    @Override
    public void execute(Storage<Flat> storage, String[] args) {

    }
}
