package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020


public class RemoveById extends AbstractCommand {
    public RemoveById() {
        command = "remove_by_id";
        helpText = "Удалить элемент из коллекции по его id.";
    }

    @Override
    public void execute(String[] args) {

    }
}
