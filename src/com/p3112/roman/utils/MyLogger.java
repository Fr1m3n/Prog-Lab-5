package com.p3112.roman.utils;
// Created by Roman Devyatilov (Fr1m3n) in 21:26 12.02.2020


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogger {
    private static MyLogger logger;
    private static final String LOG_PROPERTIES_PATH = "log.properties";

    private MyLogger() {
        Path pathToConfig = Paths.get(LOG_PROPERTIES_PATH);
        try {
            File configFile = new File(pathToConfig.toAbsolutePath().toString());
            LogManager.getLogManager().readConfiguration(new FileInputStream(configFile));

        } catch (FileNotFoundException e) {
            System.err.println("Файл с настройками для логгера не найден.");
        } catch (IOException e) {
            System.err.println("Невозможно прочитать файл с настройками логирования.");
        }
    }

//    public static Logger getLogger() {
//        if (logger == null) {
//
////            logger = LoggerFa;
//        }
//    }
}
