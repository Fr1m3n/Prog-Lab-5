package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:58 07.02.2020


import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.utils.CollectionUtils;
import com.p3112.roman.utils.UserInterfaceImpl;

import java.io.IOException;

public class InsertAt extends AbstractCommand {
    public InsertAt() {
        command = "insert_at";
        helpText = "Добавить новый элемент в заданную позицию.";
    }

    @Override
    public void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) throws IOException {
        if (args.length < 1) {
            throw new InvalidInputException("Need argument");
        }
        int pos;
        try {
            pos = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Need numerical argument");
        }
        if (pos > ss.size()) {
            userInterface.writeln("Позиция больше чем размер коллекции.");
            return;
        }
        Flat flat = CollectionUtils.readFlat(userInterface);
        ss.insertAt(pos, flat);
    }
}
