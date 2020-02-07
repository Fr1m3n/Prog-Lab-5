package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 10:00 07.02.2020


import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public class PrintFieldAscendingView extends AbstractCommand {
    public PrintFieldAscendingView() {
        command = "print_field_ascending_view";
        helpText = "Вывести значения поля view в порядке возрастания.";
    }

    @Override
    public void execute(Storage<Flat> storage, String[] args) {

    }
}
