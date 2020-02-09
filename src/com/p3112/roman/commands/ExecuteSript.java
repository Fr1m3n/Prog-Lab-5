package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020

import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.StorageService;

public class ExecuteSript extends AbstractCommand {
    public ExecuteSript() {
        command = "execute_script";
        helpText = "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {

    }
}
