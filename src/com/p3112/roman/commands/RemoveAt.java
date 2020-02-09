package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:58 07.02.2020


import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.StorageService;

public class RemoveAt extends AbstractCommand {
    public RemoveAt() {
        command = "remove_at";
        helpText = "Удалить элемент, находящийся в заданной позиции коллекции (index).";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {

    }
}
