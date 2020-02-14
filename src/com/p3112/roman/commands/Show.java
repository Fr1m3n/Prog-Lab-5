package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:55 07.02.2020


import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.StorageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Show extends AbstractCommand {
    public Show() {
        command = "show";
        helpText = "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении.";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {
        System.out.println(storage.toList());
    }
}
