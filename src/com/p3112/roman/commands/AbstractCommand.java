package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:02 07.02.2020


import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.UserInterfaceImpl;

import java.io.IOException;

/**
 * Абстрактный класс для комманд.
 */
public abstract class AbstractCommand {
    protected String command;
    protected String helpText;
    protected int argumentsCount = 0;
    protected boolean localCommand = false;

    /**
     * Метод, который описывает логику команды.
     * @param userInterface интерфейс, по которому производится обмен данными между юзером и программой
     * @param ss сервис управления коллекцией
     * @param args аргументы команды
     * @throws IOException В случае, если команда работала с I/O и произошла ошибка.
     */
    public abstract void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) throws IOException;

    public String getCommand() {
        return command;
    }

    public String getHelpText() {
        return helpText;
    }
}
