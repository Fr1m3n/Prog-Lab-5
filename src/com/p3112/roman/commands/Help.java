package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:06 07.02.2020

import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.UserInterface;

public class Help extends AbstractCommand {

    public Help() {
        command = "help";
        helpText = "Выводит справку по доступным командам.";
    }

    @Override
    public void execute(UserInterface userInterface, StorageService ss, String[] args) {
        for (AbstractCommand command : CommandsManager.getInstance().getAllCommands()) {
            userInterface.writeln(command.getCommand() + ": " + command.getHelpText());
        }
    }
}
