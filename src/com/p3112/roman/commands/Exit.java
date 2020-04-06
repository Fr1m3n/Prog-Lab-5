package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020

import com.p3112.roman.collection.StorageService;
import com.p3112.roman.network.SystemCommand;
import com.p3112.roman.utils.UserInterfaceImpl;

import java.io.IOException;

public class Exit extends AbstractCommand {
    public Exit() {
        command = "exit";
        helpText = "Отключение от сервера.";
    }

    @Override
    public void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) throws IOException {
        userInterface.write(SystemCommand.CLOSE_SESSION);
    }
}
