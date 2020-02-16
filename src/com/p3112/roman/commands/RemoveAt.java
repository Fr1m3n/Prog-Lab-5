package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:58 07.02.2020


import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.utils.UserInterface;

public class RemoveAt extends AbstractCommand {
    public RemoveAt() {
        command = "remove_at";
        helpText = "Удалить элемент, находящийся в заданной позиции коллекции (index).";
    }

    @Override
    public void execute(UserInterface userInterface, StorageService ss, String[] args) {
        if (args.length < 1) {
            throw new InvalidInputException("Need argument");
        }
        int pos;
        try {
            pos = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Need numerical argument");
        }
        userInterface.writeln("Удаляем элемент на позиции " + pos);
        if (ss.removeAt(pos)) {
            userInterface.writeln("Элемент успешно удалён");
        } else {
            userInterface.writeln("Элемента на данной позиции нет.");
        }
    }
}
