package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020

public class Clear extends AbstractCommand {
    public Clear() {
        command = "clear";
        helpText = "Очистить коллекцию.";
    }

    @Override
    public void execute(String[] args) {

    }
}
