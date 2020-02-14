package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:57 07.02.2020


import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.Storage;
import com.p3112.roman.collection.FlatDTO;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class Save extends AbstractCommand {
    private static final String PATH = "E:\\JavaProjects\\Prog-Lab-5\\out.json";
    private JsonWriter jsonWriter = new JsonWriter();

    public Save() {
        command = "save";
        helpText = "Сохранить коллекцию в файл.";
    }

    @Override
    public void execute(Storage<Flat> storage, StorageService ss, String[] args) {
        try {
            FlatDTO[] flats = new FlatDTO[storage.size()];
            storage.toList().stream().map(FlatDTO::new).collect(Collectors.toList()).toArray(flats);
            jsonWriter.writeCollectionToFile(flats, PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Сохранено успешно.");
    }
}
