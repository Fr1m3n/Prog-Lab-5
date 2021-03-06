package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020

import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.network.SystemCommand;
import com.p3112.roman.utils.UserInterfaceCLIImpl;
import com.p3112.roman.utils.UserInterfaceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ExecuteScript extends AbstractCommand {
    public ExecuteScript() {
        command = "execute_script";
        helpText = "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) throws IOException {
        if (args.length < 1) {
            throw new InvalidInputException("Команда требует аргумент.");
        }
        String scriptPath = args[0];
        userInterface.write(SystemCommand.getFileRequestCommand(scriptPath));

    }
}
