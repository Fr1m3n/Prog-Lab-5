package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020

import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.InvalidInputException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ExecuteSript extends AbstractCommand {
    public ExecuteSript() {
        command = "execute_script";
        helpText = "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {
        if (args.length < 1) {
            throw new InvalidInputException("Need argument");
        }
        String script_path;
        try {
            script_path = args[0];
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Need numerical argument");
        }
        Path pathToScript = Paths.get(script_path);
        if (!pathToScript.toFile().isFile()) {
            throw new InvalidInputException("File is not exist");
        }

    }
}
