package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:55 07.02.2020


import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.UserInterface;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Show extends AbstractCommand {
    public Show() {
        command = "show";
        helpText = "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении.";
    }

    @Override
    public void execute(UserInterface userInterface, StorageService ss, String[] args) {
        try {
            userInterface.write(ss.show());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
