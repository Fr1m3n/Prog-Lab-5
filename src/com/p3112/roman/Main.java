package com.p3112.roman;

import com.p3112.roman.cli.CliApplication;
import com.p3112.roman.collection.*;

import com.p3112.roman.commands.CommandsManager;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.exceptions.NoSuchCommandException;
import com.p3112.roman.network.client.Client;
import com.p3112.roman.network.server.Server;
import com.p3112.roman.utils.CollectionUtils;
import com.p3112.roman.utils.JsonReader;
import com.p3112.roman.utils.UserInterfaceCLIImpl;
import com.p3112.roman.utils.UserInterfaceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Главный класс CLI приложения.
 */
@Slf4j
public class Main {

    /**
     * Точка входа.
     *
     * @param args аргументы из командной строки
     */
    public static void main(String[] args) {
        String mode = System.getProperty("mode").toLowerCase();
        CustomRunnable application;
        switch (mode) {
            case ("cli"):
                application = new CliApplication();
                break;
            case ("server"):
                application = new Server();
                break;
            case ("client"):
                application = new Client();
                break;
            default:
                System.out.println("Не указана переменная окружения mode.\n " +
                        "Присвойте ей значение server или client в зависимости от необходимого режима работы. \n" +
                        "По умолчанию запущен клиент.");
                application = new Client();
                break;
        }
        application.run(args);
    }
}
