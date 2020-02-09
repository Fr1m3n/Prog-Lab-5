package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020

import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.StorageService;

public class Clear extends AbstractCommand {
    public Clear() {
        command = "clear";
        helpText = "Очистить коллекцию.";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {
        storage.clear();
    }
}
