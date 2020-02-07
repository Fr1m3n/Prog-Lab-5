package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 10:00 07.02.2020

import com.p3112.roman.Flat;
import com.p3112.roman.Storage;

public class FilterLessThanNumberOfRooms extends AbstractCommand {
    public FilterLessThanNumberOfRooms() {
        command = "filter_less_than_number_of_rooms";
        helpText = "Вывести элементы, значение поля numberOfRooms которых меньше заданного.";
    }

    @Override
    public void execute(Storage<Flat> storage, String[] args) {

    }
}
