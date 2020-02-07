package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020


import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public class RemoveById extends AbstractCommand {
    public RemoveById() {
        command = "remove_by_id";
        helpText = "Удалить элемент из коллекции по его id.";
    }

    @Override
    public void execute(Storage<Flat> storage, String[] args) {

    }
}
