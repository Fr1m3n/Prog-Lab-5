package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 10:00 07.02.2020

import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.InvalidInputException;

import java.util.List;
import java.util.stream.Collectors;

public class FilterLessThanNumberOfRooms extends AbstractCommand {
    public FilterLessThanNumberOfRooms() {
        command = "filter_less_than_number_of_rooms";
        helpText = "Вывести элементы, значение поля numberOfRooms которых меньше заданного.";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {
        if (args.length < 1) {
            throw new InvalidInputException("Need argument");
        }
        long numOfRooms;
        try {
            numOfRooms = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Need numerical argument");
        }
        List<Flat> res = storage.toList().stream().filter(x -> x.getNumberOfRooms() < numOfRooms).collect(Collectors.toList());
        System.out.println(res);
    }
}
