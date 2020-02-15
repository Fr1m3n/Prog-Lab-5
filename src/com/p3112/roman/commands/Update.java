package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:56 07.02.2020


import com.p3112.roman.collection.Flat;
import com.p3112.roman.collection.StorageService;
import com.p3112.roman.utils.UserInterface;

public class Update extends AbstractCommand {
    public Update() {
        command = "update";
        helpText = "Обновить значение элемента коллекции, id которого равен заданному.";
    }

    @Override
    public void execute(UserInterface userInterface, StorageService ss, String[] args) {
        long id;
        try {
            id = Long.parseLong(args[1]);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        Flat flat = userInterface.readFlat();
        ss.update(id, flat);
//        Flat flat = InputUtils.readFlatFromStream(System.in);
//        long id = flat.getId();
//        List<Flat> flats = userInterface.toList().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        
    }
}
