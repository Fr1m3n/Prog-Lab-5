package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:55 07.02.2020


public class Show extends AbstractCommand {
    public Show() {
        command = "show";
        helpText = "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении.";
    }

    @Override
    public void execute(String[] args) {

    }
}
