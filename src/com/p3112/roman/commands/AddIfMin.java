package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:58 07.02.2020

import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.CollectionUtils;
import com.p3112.roman.utils.UserInterfaceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AddIfMin extends AbstractCommand {
    public AddIfMin() {
        command = "add_if_min";
        helpText = "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции.";
    }

    @Override
    public void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) throws IOException {
        Flat flat = CollectionUtils.readFlat(userInterface);
        boolean success = ss.addIfMin(flat);
        if (success) {
            userInterface.writeln("Объект успешно добавлен в коллекцию.");
        } else {
            userInterface.writeln("Объект оказался больше минимального в коллекции. Объект не был добавлен.");
        }
    }
}
