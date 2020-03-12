package com.p3112.roman.server;
// Created by Roman Devyatilov (Fr1m3n) in 21:26 08.03.2020


import com.p3112.roman.collection.*;
import com.p3112.roman.commands.CommandsManager;
import com.p3112.roman.exceptions.NoSuchCommandException;
import com.p3112.roman.utils.UserInterface;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class Server implements Runnable {


    private ServerSocket server;

    public static void main(String[] args) {
        Server s = new Server();
        s.run();
    }

    private void startServer(int port) throws IOException {
        server = new ServerSocket(port);
        log.info("Сервер успешно запущен. Слушается порт: {}", server.getLocalPort());
    }


    @Override
    public void run() {
        try {
            startServer(5858);
            Storage<Flat> storage = new StackFlatStorageImpl();
            StorageService storageService = new StackFlatStorageService(storage);
            Socket socket = server.accept();
            log.info("Присоединился клиент. PORT: {} IP: {}", socket.getInetAddress().getHostAddress(), socket.getPort());
            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            UserInterface userInterface = new UserInterface(new InputStreamReader(socket.getInputStream()), new OutputStreamWriter(socket.getOutputStream()), false);
            while (true) {
                try {
                    String s;
                    if (userInterface.hasNextLine()) {
                        s = userInterface.read().trim();
                        if (!s.isEmpty()) {
                            try {
                                CommandsManager.getInstance().executeCommand(userInterface, storageService, s);
                            } catch (NoSuchCommandException e) {
                                userInterface.writeln("Такой команды нет, бро");
                            }
                        }
                        log.info("Пользователь прислал строку: {}", s);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
