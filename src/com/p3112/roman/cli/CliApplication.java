package com.p3112.roman.cli;
// Created by Roman Devyatilov (Fr1m3n) in 21:37 08.03.2020


import com.p3112.roman.CustomRunnable;
import com.p3112.roman.collection.*;
import com.p3112.roman.commands.CommandsManager;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.exceptions.NoSuchCommandException;
import com.p3112.roman.utils.CollectionUtils;
import com.p3112.roman.utils.JsonReader;
import com.p3112.roman.utils.UserInterfaceCLIImpl;
import com.p3112.roman.utils.UserInterfaceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Slf4j
public class CliApplication implements CustomRunnable {

    public void run(String[] args) {
        log.info("CLI запущен.");
        Storage<Flat> storage = new StackFlatStorageImpl();
        JsonReader jsonReader = new JsonReader();
        UserInterfaceImpl cliInterface = new UserInterfaceCLIImpl(
                System.in,
                System.out,
                true);
        StorageService storageService = new StackFlatStorageService(storage);

        log.info("Инициализация коллекции и вспомогательных классов прошла успешно!");
        if (args.length > 0) {
            Path pathToInitFile = Paths.get(args[0]);
            boolean success = false;
            try {
                FlatDTO[] flats = jsonReader.readCollectionFromFile(pathToInitFile.toAbsolutePath().toString());
                for (FlatDTO flatDTO : flats) {
                    flatDTO.checkFields();
                    Flat flat = flatDTO.toFlat();
                    if (CollectionUtils.checkNumber(flat.getNumberOfRooms(), 0, 18)) {
                        storage.put(flat);
                    } else {
                        log.error("Запись номер с полем name равным: {} - не прошла проверку на границы числа.", flat.getId());
                    }
                }
                cliInterface.writeln("Успешно добавлено " + storage.size() + " из " + flats.length + " записей.");
                success = true;
            } catch (FileNotFoundException e) {
                cliInterface.writeln("Что-то пошло не так во время открытия файла. Проверьте его существование и права на него. Для более подробной информации смотри в лог.");
                log.error("Ошибка при открытии файла {}. Текст ошибки {}", args[0], e.getMessage());
            } catch (IOException e) {
                cliInterface.writeln("Что-то пошло не так во время считывния файла, смотрите логи.");
                log.error("Ошибка ввода/вывода во время считывания файла {}. {}", args[0], e.getMessage());
            } catch (ClassCastException e) {
                log.error("Ошибка приведения типов. {}", e.getMessage());
                cliInterface.writeln("Ошибка во время каста. Смотрите логи для более подробной информации об ошибке.");
            } catch (InvalidInputException e) {
                cliInterface.writeln("Ошибка во время проверки данных. Что-то во входном файле не так. Проверьте логи.");
                log.error("Одно из полей не подходит под ограничения. {}", e.getMessage());
            } catch (Exception e) {
                cliInterface.writeln("Меня написал дебил, который что-то не проверил... Там в логах должна быть инфа об ошибке.");
                log.error("Неизвестная ошибка. {}", e.getMessage());
            } finally {
                if (!success) {
                    cliInterface.writeln("Что-то пошло не так при инициализации коллекции. Теперь вы работаете с пустой коллекцией.");
                }
            }
        } else {
            cliInterface.writeln("Файл для инициализации коллекции не введён. Имя файла следует ввести в аргументах запуска программы.");
        }
        while (true) {
            if (cliInterface.hasNextLine()) {
                String cmd = "NULL_COMMAND";
                try {
                    cmd = cliInterface.read();
                } catch (IOException e) {
                    log.error("Ошибка ввода/вывода.");
                }
                try {
                    CommandsManager.getInstance().executeCommand(cliInterface, storageService, cmd.trim());
                } catch (NoSuchCommandException e) {
                    cliInterface.writeln("Неизвестная команда, используйте команду help, чтобы посмотреть список всех доступных команд.");
                } catch (InvalidInputException e) {
                    cliInterface.writeln("Ошибка в введённых пользователем данных. Проверьте введённые данные на валидность и попробуйте ещё разок. Для более подробной информации смотреть в лог.");
                    log.error("Не верный ввод от пользователя. {}", e.getMessage());
                } catch (NumberFormatException e) {
                    cliInterface.writeln("Введено не корректное число. Смотрите в логи для более подробной информации.");
                    log.error("Ошибка парсинга числа. {}", e.getMessage());
                } catch (IOException e) {
                    cliInterface.writeln("Ошибка ввода/вывода. Смотрите в логи для более подробной информации.");
                    log.error("Ошибка ввода/вывода. {}", e.getMessage());
                } catch (Exception e) {
                    cliInterface.writeln("Произошла неизвестная ошибка. Прогер дурачок. Чекай логи...");
                    log.error("Неизвестная ошибка. {}", e.getMessage());
                }
            }
        }
    }
}
