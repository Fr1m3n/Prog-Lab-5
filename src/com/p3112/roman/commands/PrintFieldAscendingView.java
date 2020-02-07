package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 10:00 07.02.2020


public class PrintFieldAscendingView extends AbstractCommand {
    public PrintFieldAscendingView() {
        command = "print_field_ascending_view";
        helpText = "Вывести значения поля view в порядке возрастания.";
    }

    @Override
    public void execute(String[] args) {

    }
}
