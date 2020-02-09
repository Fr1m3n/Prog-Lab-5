package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020

import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.StorageService;

public class Exit extends AbstractCommand {
    public Exit() {
        command = "exit";
        helpText = "Выход из программы без сохранения.";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {
        System.exit(0);
    }
}
