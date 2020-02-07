package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:02 07.02.2020


import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public abstract class AbstractCommand {
    protected String command;
    protected String helpText;

    public abstract void execute(Storage<Flat> storage, String[] args);

    public String getCommand() {
        return command;
    }

    public String getHelpText() {
        return helpText;
    }
}
