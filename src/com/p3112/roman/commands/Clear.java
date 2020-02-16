package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020

import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.UserInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Clear extends AbstractCommand {
    public Clear() {
        command = "clear";
        helpText = "Очистить коллекцию.";
    }

    @Override
    public void execute(UserInterface userInterface, StorageService ss, String[] args) {
        int count = ss.size();
        ss.clear();
        userInterface.writeln("Коллекция успешно очищенна! Элементов удалено: " + count);
        log.info("Коллекция очищенна.");
    }
}
