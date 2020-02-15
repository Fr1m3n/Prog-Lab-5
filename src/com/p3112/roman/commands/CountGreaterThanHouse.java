package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:59 07.02.2020

import com.p3112.roman.collection.House;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.UserInterface;

public class CountGreaterThanHouse extends AbstractCommand {
    public CountGreaterThanHouse() {
        command = "count_greater_than_house";
        helpText = "Вывести количество элементов, значение поля house которых больше заданного.";
    }

    @Override
    public void execute(UserInterface userInterface, StorageService ss, String[] args) {
        House house = userInterface.readHouse();
        ss.countGreaterThanHouse(house);

    }
}
