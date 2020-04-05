package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020


import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.utils.CollectionUtils;
import com.p3112.roman.utils.UserInterfaceImpl;

import java.io.IOException;

public class Update extends AbstractCommand {
    public Update() {
        command = "update";
        helpText = "Обновить значение элемента коллекции, id которого равен заданному.";
        argumentsCount = 1;
    }

    @Override
    public void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) throws IOException {
        if (args.length < 1) {
            throw new InvalidInputException("Введено " + args.length + " аргументов, ожидалось " + argumentsCount);
        }
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        Flat flat = CollectionUtils.readFlat(userInterface);
        ss.update(id, flat);
//        Flat flat = InputUtils.readFlatFromStream(System.in);
//        long id = flat.getId();
//        List<Flat> flats = userInterface.toList().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        
    }
}
