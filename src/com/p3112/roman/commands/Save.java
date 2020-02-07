package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020


import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public class Save extends AbstractCommand {
    public Save() {
        command = "save";
        helpText = "Сохранить коллекцию в файл.";
    }

    @Override
    public void execute(Storage<Flat> storage, String[] args) {

    }
}
