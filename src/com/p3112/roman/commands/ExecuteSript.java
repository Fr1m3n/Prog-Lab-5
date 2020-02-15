package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020

import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.utils.UserInterface;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ExecuteSript extends AbstractCommand {
    public ExecuteSript() {
        command = "execute_script";
        helpText = "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public void execute(UserInterface userInterface, StorageService ss, String[] args) {
        if (args.length < 1) {
            throw new InvalidInputException("Need argument");
        }
        String script_path = args[0];
        Path pathToScript = Paths.get(script_path);
        try {
            UserInterface fileInterface = new UserInterface(new FileReader(pathToScript.toFile()), new OutputStreamWriter(System.out), false);
            while (fileInterface.hasNextLine()) {
                String line = fileInterface.read();
                CommandsManager.getInstance().executeCommand(fileInterface, ss, line);
            }
        } catch (FileNotFoundException e) {
            log.error("File is not exist");
        }
    }
}
