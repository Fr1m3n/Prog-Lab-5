package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:02 07.02.2020


import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.UserInterface;

import java.io.IOException;

public abstract class AbstractCommand {
    protected String command;
    protected String helpText;
    protected int argumentsCount = 0;

    public abstract void execute(UserInterface userInterface, StorageService ss, String[] args) throws IOException;

    public String getCommand() {
        return command;
    }

    public String getHelpText() {
        return helpText;
    }
}
