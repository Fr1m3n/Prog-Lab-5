package com.p3112.roman.network.server;
// Created by Roman Devyatilov (Fr1m3n) in 21:26 08.03.2020


import com.p3112.roman.CustomRunnable;
import com.p3112.roman.collection.*;
import com.p3112.roman.commands.CommandsManager;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.exceptions.NoSuchCommandException;
import com.p3112.roman.network.SystemCommand;
import com.p3112.roman.utils.CollectionUtils;
import com.p3112.roman.utils.UserInterfaceImpl;
import com.p3112.roman.network.UserInterfaceNetworkSocketImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class Server implements CustomRunnable {


    private ServerSocket server;
    private boolean running;

    public static void main(String[] args) {
        CustomRunnable s = new Server();
        s.run(args);
    }


    /**
     * Запускает сервер
     * @param port порт, который будет слушать сервер
     * @throws IOException I/O ошибка
     */
    private void startServer(int port) throws IOException {
        server = new ServerSocket(port, 1);
        log.info("Сервер успешно запущен. Слушается порт: {}", server.getLocalPort());
    }

    private void stopServer() {
        running = false;
    }


    /**
     * Инициализирует класс коллекции и сервис для работы с ней.
     * @return Объект сервиса, для работы с созданной коллекцией
     */
    private StorageService initializeCollection(Path initFile) throws IOException {
        Storage<Flat> storage = new StackFlatStorageImpl();
        StorageService st = new StackFlatStorageService(storage);
        try {
            st.load(initFile);
            log.info("Коллекция успешно инициализированна из файла {}", initFile.getFileName());
        } catch (FileNotFoundException e) {
            log.error("Ошибка при открытии файла. Используется пустая коллекция.");
        }
        return st;
    }


    @Override
    public void run(String[] args) {
        try {
            running = true;
            if (args.length == 1) {
                startServer(Integer.parseInt(args[0]));
            } else {
                startServer(5858);
                log.info("Невозможно вычислить значение порта из аргументов.\n" +
                        "Укажите его первым аргументом.\n" +
                        "Сервер запущен и слушает порт по умолчанию 5858");
            }
            StorageService storageService = initializeCollection(Paths.get("in.json"));
            while (running) {
                Socket socket = server.accept(); // ловим подключившегося юзера
                log.info("Присоединился клиент. PORT: {} IP: {}", socket.getInetAddress().getHostAddress(), socket.getPort());
                UserInterfaceImpl userInterface = new UserInterfaceNetworkSocketImpl(socket.getInputStream(), socket.getOutputStream(), true);
                String s = userInterface.read();
                System.out.println(CollectionUtils.writeHexBytes(s.getBytes()));
                if (SystemCommand.equals(s.getBytes(), SystemCommand.IS_READY)) {
                    userInterface.write(SystemCommand.READY);
                }
                while (true) {
                    try {

                        s = userInterface.read();
                        if (s == null) {
                            continue;
                        }
                        log.info("Пользователь прислал строку: {}", s);
                        if (!s.isEmpty()) {
                            try {
                                log.info("Пытаемся исполнить команду пользователя {}", s);
                                CommandsManager.getInstance().executeCommand(userInterface, storageService, s.trim());
                            } catch (NoSuchCommandException e) {
                                log.error("{}: такой команды не существует.", s);
                                userInterface.writeln("Такой команды нет, бро");
                            } catch (InvalidInputException e) {
                                log.error("Произошла проблема с аргументами команды.", e);
                                userInterface.writeln("Вы не указали обязательный аргумент для команды.");
                            } catch (NumberFormatException e) {
                                log.error("Ошибка парсинга числа");
                                userInterface.writeln("Ожидалось число. Пришло не число. Команда отменена.");
                            } catch (Exception e) {
                                log.error("Неизвестная ошибка", e);
                                userInterface.writeln("Произошла неизвестная ошибка.");
                            }
                        }
                    } catch (IOException e) {
                        log.error("Потеряно соединение. Клиент отключился.");
                        e.printStackTrace();
                        socket.close();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
