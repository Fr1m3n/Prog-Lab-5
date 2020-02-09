package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:06 07.02.2020

import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.StorageService;

public class Help extends AbstractCommand {

    public Help() {
        command = "help";
        helpText = "Выводит справку по доступным командам.";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {
        for (AbstractCommand command : CommandsManager.getInstance().getAllCommands()) {
            System.out.println(command.getCommand() + ": " + command.getHelpText());
        }
    }
}
