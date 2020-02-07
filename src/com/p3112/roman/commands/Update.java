package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020


import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public class Update extends AbstractCommand {
    public Update() {
        command = "update";
        helpText = "Обновить значение элемента коллекции, id которого равен заданному.";
    }

    @Override
    public void execute(Storage<Flat> storage, String[] args) {

    }
}
