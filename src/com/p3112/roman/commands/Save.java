package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020


import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.JsonWriter;
import com.p3112.roman.utils.UserInterfaceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
public class Save extends AbstractCommand {
    private static final String PATH = Paths.get("out.json").toAbsolutePath().toString();
    private JsonWriter jsonWriter = new JsonWriter();

    public Save() {
        command = "save";
        helpText = "Сохранить коллекцию в файл.";
    }

    @Override
    public void execute(UserInterfaceImpl userInterface, StorageService ss, String[] args) throws IOException {
        ss.save(PATH);
        userInterface.writeln("Коллекция сохранена успешно.");
        log.info("Сохранено успешно.");
    }
}
