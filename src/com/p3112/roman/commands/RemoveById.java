package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020


import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.utils.UserInterfaceImpl;

public class RemoveById extends AbstractCommand {
    public RemoveById() {
        command = "remove_by_id";
        helpText = "Удалить элемент из коллекции по его id.";
    }

    @Override
    public void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) {
        if (args.length < 1) {
            throw new InvalidInputException("Need argument");
        }
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Need numerical argument");
        }
        if (ss.removeById(id)) {
            userInterface.writeln("Квартира с id " + id + " успешно удалена!");
        } else {
            userInterface.writeln("Квартира с id " + id + " не найдена.");
        }
    }
}
