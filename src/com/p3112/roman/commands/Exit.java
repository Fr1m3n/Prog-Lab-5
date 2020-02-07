package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020

public class Exit extends AbstractCommand {
    public Exit() {
        command = "exit";
        helpText = "Выход из программы без сохранения.";
    }

    @Override
    public void execute(String[] args) {
        System.exit(0);
    }
}
