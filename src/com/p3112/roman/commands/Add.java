package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:55 07.02.2020

import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.CollectionUtils;
import com.p3112.roman.utils.UserInterfaceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Add extends AbstractCommand {

    public Add() {
        command = "add";
        helpText = "Добавить новый элемент в коллекцию. Принимает один аргумент.";
    }

    @Override
    public void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) throws IOException {
        Flat flat = CollectionUtils.readFlat(userInterface);
        ss.add(flat);
        userInterface.writeln("Квартира успешно добавлена!");
        log.info("Квартира успешно добавлена. Ей присвоен id: {}", flat.getId());

    }
}
