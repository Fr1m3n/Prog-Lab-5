package com.p3112.roman;

import com.p3112.roman.collection.*;
import com.p3112.roman.commands.CommandsManager;
import com.p3112.roman.exceptions.NoSuchCommandException;
import com.p3112.roman.utils.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Storage<Flat> storage = new StackFlatStorageImpl();
        JsonReader jsonReader = new JsonReader();
        StorageService storageService = new StackFlatStorageService(storage);
        Path pathToInitFile = Paths.get(args[0]);
        File initFile = pathToInitFile.toFile();
        try {
            FlatDTO[] flats = jsonReader.readCollectionFromFile(pathToInitFile.toAbsolutePath().toString());
//            flats.getStorage().forEach(x -> storage.put((Flat)x));
            for (FlatDTO flatDTO : flats) {
                Flat flat = flatDTO.toFlat();

                storage.put(flat);
            }

            System.out.println("УСПЕШНО СЧИТАЛИ!");
        } catch (FileNotFoundException e) {
            System.out.println("Что-то пошло не так во время открытия файла. Проверьте его существование и права на него. Обратитесь к администратору, короче...");;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.out.println("Ошибка во время каста.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Что-то ещё произошло...");
        }

        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String cmd = scanner.nextLine();
                String[] ar = cmd.split(" ");
                cmd = ar[0];
                ar = Arrays.copyOfRange(ar, 1, ar.length); // удалим первый элемент, оставив аргументы
                try {
                    CommandsManager.getInstance().getCommand(cmd).execute(storage, storageService, ar);
                } catch (NoSuchCommandException e) {
                    System.out.println("Неизвестная команда, используйте команду help, чтобы посмотреть список всех доступных команд.");
                }
            }
        }
    }
}
