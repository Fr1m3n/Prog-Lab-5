package com.p3112.roman;

import com.p3112.roman.collection.*;
import com.p3112.roman.commands.CommandsManager;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.exceptions.NoSuchCommandException;
import com.p3112.roman.utils.JsonReader;
import com.p3112.roman.utils.UserInterface;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("CLI запущен.");
        Storage<Flat> storage = new StackFlatStorageImpl();
        JsonReader jsonReader = new JsonReader();
        UserInterface cliInterface = new UserInterface(
                new InputStreamReader(System.in, StandardCharsets.UTF_8),
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8),
                true);
        StorageService storageService = new StackFlatStorageService(storage);
        Path pathToInitFile = Paths.get(args[0]);
        log.info("Инициализация коллекции и вспомогательных классов прошла успешно!");
        try {
            FlatDTO[] flats = jsonReader.readCollectionFromFile(pathToInitFile.toAbsolutePath().toString());
            for (FlatDTO flatDTO : flats) {
                Flat flat = flatDTO.toFlat();
                storage.put(flat);
            }
            cliInterface.writeln("Успешно считано " + flats.length + " записей.");
        } catch (FileNotFoundException e) {
            cliInterface.writeln("Что-то пошло не так во время открытия файла. Проверьте его существование и права на него. Для более подробной информации смотри в лог.");
            log.error("Ошибка при открытии файла {}. Текст ошибки {}", args[0], e.getMessage());
        } catch (IOException e) {
            cliInterface.writeln("Что-то пошло не так во время считывния файла, смотрите логи.");
            log.error("Ошибка ввода/вывода во время считывания файла {}. {}", args[0], e.getMessage());
        } catch (ClassCastException e) {
            log.error("Ошибка приведения типов. {}", e.getMessage());
            cliInterface.writeln("Ошибка во время каста. Смотрите логи для более подробной информации об ошибке.");
        } catch (Exception e) {
            cliInterface.writeln("Меня написал дебил, который что-то не проверил... Там в логах должна быть инфа об ошибке.");
            log.error("Неизвестная ошибка. {}", e.getMessage());
        }

        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String cmd = scanner.nextLine();
                String[] ar = cmd.split(" ");
                cmd = ar[0];
                ar = Arrays.copyOfRange(ar, 1, ar.length); // удалим первый элемент, оставив аргументы
                try {
                    CommandsManager.getInstance().getCommand(cmd).execute(cliInterface, storageService, ar);
                } catch (NoSuchCommandException e) {
                    cliInterface.writeln("Неизвестная команда, используйте команду help, чтобы посмотреть список всех доступных команд.");
                } catch (InvalidInputException e) {
                    cliInterface.writeln("Ошибка в введённых пользователем данных. Проверьте введённые данные на валидность и попробуйте ещё разок. Для более подробной информации смотреть в лог.");
                    log.error("Не верный ввод от пользователя. {}", e.getMessage());
                } catch (NumberFormatException e) {
                    cliInterface.writeln("Введено не корректное число. Смотрите в логи для более подробной информации.");
                    log.error("Ошибка парсинга числа. {}", e.getMessage());
                }
            }
        }
    }
}
