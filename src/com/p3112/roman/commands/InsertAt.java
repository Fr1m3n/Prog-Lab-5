package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:58 07.02.2020


import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public class InsertAt extends AbstractCommand {
    public InsertAt() {
        command = "insert_at";
        helpText = "Добавить новый элемент в заданную позицию.";
    }

    @Override
    public void execute(Storage<Flat> storage, String[] args) {

    }
}
