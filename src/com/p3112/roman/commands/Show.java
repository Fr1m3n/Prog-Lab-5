package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:55 07.02.2020


import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public class Show extends AbstractCommand {
    public Show() {
        command = "show";
        helpText = "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении.";
    }

    @Override
    public void execute(Storage<Flat> storage, String[] args) {

    }
}
