package com.p3112.roman.client;
// Created by Roman Devyatilov (Fr1m3n) in 20:58 08.03.2020


import com.p3112.roman.utils.UserInterface;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.stream.Stream;

@Slf4j
public class Client implements Runnable {

    private SocketChannel socketChannel;
    private ByteBuffer outBuffer;
    private ByteBuffer inBuffer;
    private UserInterface userInterface = new UserInterface(new InputStreamReader(System.in), new OutputStreamWriter(System.out), true);
    private Selector selector;

    public Client() {
        outBuffer = ByteBuffer.allocate(512);
        inBuffer = ByteBuffer.allocate(2048);
    }

    // для тестов
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    /**
     * Подключается к серверу
     * @param host домен сервера
     * @param port порт сервера
     * @throws IOException ошибка подключения
     */
    private void connect(String host, int port) throws IOException {
        userInterface.writeln("===============\nConnecting to the server...\n Host: " + host + "\n Port: " + port);
        socketChannel = SocketChannel.open(new InetSocketAddress(5858));
        socketChannel.configureBlocking(false);
        selector = SelectorProvider.provider().openSelector();
        userInterface.writeln("Successfully connected to server!\n===============");
    }

    /**
     * Отправляет строку серверу.
     * @param data строка, которая будет отправлена
     * @throws IOException
     */
    private void sendData(String data) throws IOException {
        log.info("Отправляем строку: {}", data);
        outBuffer.clear();
        outBuffer.put((data + "\n\r").getBytes());
        outBuffer.flip();
        while (outBuffer.hasRemaining()) {
            log.info("Успешно отправленно {} байт данных.", socketChannel.write(outBuffer));
        }
        log.info("Строка отправлена...");
        socketChannel.register(selector, SelectionKey.OP_READ);

    }

    private String readData() throws IOException {
        log.info("Пытаемся читать вывод от сервера.");
        inBuffer.clear();
        socketChannel.read(inBuffer);
        inBuffer.flip();
        socketChannel.register(selector, SelectionKey.OP_WRITE);

        return new String(inBuffer.array());
    }

    private void disconnect() throws IOException {
        socketChannel.close();
    }

    /**
     * Точка входа для клиента.
     */
    @Override
    public void run() {
        try {
            connect("localhost", 5858);
            String userInput;
            while (!socketChannel.finishConnect()) {}
            while (socketChannel.isConnected()) {
                userInput = userInterface.readWithMessage("#-> ", true);
                sendData(userInput);
                Thread.sleep(20);
                System.out.println(readData() + "!");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
