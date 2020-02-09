package com.p3112.roman;

import com.p3112.roman.collection.*;
import com.p3112.roman.commands.CommandsManager;
import com.p3112.roman.exceptions.NoSuchCommandException;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Storage<Flat> storage = new StackFlatStorageImpl();
        StorageService storageService = new StackFlatStorageService(storage);

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
