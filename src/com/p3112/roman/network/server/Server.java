package com.p3112.roman.network.server;
// Created by Roman Devyatilov (Fr1m3n) in 21:26 08.03.2020


import com.p3112.roman.collection.*;
import com.p3112.roman.commands.CommandsManager;
import com.p3112.roman.exceptions.NoSuchCommandException;
import com.p3112.roman.network.SystemCommand;
import com.p3112.roman.utils.UserInterfaceImpl;
import com.p3112.roman.network.UserInterfaceNetworkSocketImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Server implements Runnable {


    private ServerSocket server;
    private boolean running;

    public static void main(String[] args) {
        Server s = new Server();
        s.run();
    }

    private void startServer(int port) throws IOException {
        server = new ServerSocket(port, 1);
        log.info("Сервер успешно запущен. Слушается порт: {}", server.getLocalPort());
    }

    private StorageService initializeCollection() {
        Storage<Flat> storage = new StackFlatStorageImpl();
        return new StackFlatStorageService(storage);
    }


    @Override
    public void run() {
        try {
            running = true;
            startServer(5858);
            StorageService storageService = initializeCollection();
            while (running) {
                Socket socket = server.accept(); // ловим подключившегося юзера
                log.info("Присоединился клиент. PORT: {} IP: {}", socket.getInetAddress().getHostAddress(), socket.getPort());
                UserInterfaceImpl userInterface = new UserInterfaceNetworkSocketImpl(socket.getInputStream(), socket.getOutputStream(), true);
                String s = userInterface.read();
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
                            }
                        }
                    } catch (Exception e) {
                        log.error("Потеряно соединение. С ошибкой {}", e.getMessage());
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
