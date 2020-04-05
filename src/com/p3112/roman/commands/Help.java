package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:06 07.02.2020

import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.UserInterfaceImpl;

import java.io.IOException;

public class Help extends AbstractCommand {

    public Help() {
        command = "help";
        helpText = "Выводит справку по доступным командам.";
    }

    @Override
    public void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (AbstractCommand command : CommandsManager.getInstance().getAllCommands()) {
            sb.append(command.getCommand()).append(": ").append(command.getHelpText()).append('\n');
        }
        userInterface.write(sb.toString());
    }
}
