package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020


import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.InvalidInputException;

import java.util.List;
import java.util.stream.Collectors;

public class RemoveById extends AbstractCommand {
    public RemoveById() {
        command = "remove_by_id";
        helpText = "Удалить элемент из коллекции по его id.";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {
        if (args.length < 1) {
            throw new InvalidInputException("Need argument");
        }
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Need numerical argument");
        }
        List<Flat> flats = List.copyOf(storage.toList());
        flats.stream().filter(x -> x.getId() == id).forEach(storage::remove);
        System.out.printf("Элемент с id равным %d успешно удалён!", id);
    }
}
