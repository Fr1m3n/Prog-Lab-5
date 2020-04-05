package com.p3112.roman.network.client;
// Created by Roman Devyatilov (Fr1m3n) in 20:58 08.03.2020


import com.p3112.roman.network.Request;
import com.p3112.roman.network.SystemCommand;
import com.p3112.roman.utils.CollectionUtils;
import com.p3112.roman.utils.CustomByteInputStream;
import com.p3112.roman.utils.UserInterfaceCLIImpl;
import com.p3112.roman.utils.UserInterfaceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

@Slf4j
public class Client implements Runnable {

    private SocketChannel socketChannel;
    private ByteBuffer buffer;
    private UserInterfaceImpl cliUserInterface;
    private UserInterfaceImpl networkUserInterface;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ByteArrayInputStream byteArrayInputStream;
    public static final char[] WAITING_ANIMATION = {'|', '/', '-', '\\', '-'};



    public Client() {
        buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        cliUserInterface = new UserInterfaceCLIImpl(System.in, System.out, true);
        byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        byteArrayOutputStream = new ByteArrayOutputStream();

        networkUserInterface = new UserInterfaceImpl(byteArrayInputStream, byteArrayOutputStream, true){

            @Override
            public void write(String message) throws IOException {
                if (message == null) {
                    log.debug("Пришла null-строка");
                    message = "";
                }
                log.debug("Отправляем строку: {}", message);
                buffer.clear();
                Request request = new Request(message);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                objectOutputStream.writeObject(request);
                log.debug("Отправляю байты: {}", CollectionUtils.writeHexBytes(out.toByteArray()));
                buffer.put(out.toByteArray());
                buffer.flip();
                while (buffer.hasRemaining()) {
                    log.debug("Успешно отправленно {} байт данных.", socketChannel.write(buffer));
                }
                log.debug("Строка отправлена...");
            }

            @Override
            public String read() throws IOException {
                log.info("Пытаемся читать вывод от сервера.");
//                buffer = ByteBuffer.allocate(1024);
                buffer.clear();
                byte[] temp = new byte[0]; // в этом массиве будем хранить данные принятые с сервера
                while (true) {
                    int receivedBytesCount = socketChannel.read(buffer);
                    if (receivedBytesCount > 0) {
                        do {
                            buffer.flip();
                            if (!buffer.hasArray()) {
                                log.error("Нет массива в буфере...");
                                return null;
                            }
                            log.info("Принял {} байт данных: \n {}", receivedBytesCount, CollectionUtils.writeHexBytes(buffer.array()));
                            temp = concatByteArrays(temp, Arrays.copyOf(buffer.array(), receivedBytesCount));
                            buffer.clear();
                            receivedBytesCount = socketChannel.read(buffer);
                        } while (receivedBytesCount != 0);
                        break;
                    }
                }
                temp = fixHeaders(temp); // пытаемся починить хедеры
                log.debug("Принял байты: {}", CollectionUtils.writeHexBytes(temp));
                ObjectInputStream objectInputStream = new ObjectInputStream(new CustomByteInputStream(temp));
                try {
                    Request request = (Request) objectInputStream.readObject();
                    return request.getContentByString();
                } catch (ClassNotFoundException e) {
                    throw new IOException("Class not found");
                }
            }
        };
    }

    private byte[] concatByteArrays(byte[] a, byte[] b) {
        byte[] c = Arrays.copyOf(a, a.length + b.length);
        for (int i = 0; i < b.length; i++) {
            c[a.length + i] = b[i];
        }
        return c;
    }

    // для тестов
    public static void main(String[] args) {
        Runnable client = new Client();
        client.run();
    }

    private byte[] fixHeaders(byte[] b) {
        short q = ObjectStreamConstants.STREAM_MAGIC;
        short z = ObjectStreamConstants.STREAM_VERSION;
        if (getShort(b, 0) != q || getShort(b, 2) != z) {
            log.info("Фиксим хедеры.");
            byte[] newTemp = new byte[b.length + 4];
            putShort(newTemp, 0, q);
            putShort(newTemp, 2, z);
            for (int i = 0; i < b.length; i++) {
                newTemp[i + 4] = b[i];
            }
            return newTemp;
        }
        return b;
    }

    private void putShort(byte[] b, int off, short val) {
        b[off + 1] = (byte) (val);
        b[off] = (byte) (val >>> 8);
    }

    private short getShort(byte[] b, int off) {
        return (short) ((b[off + 1] & 0xFF) +
                (b[off] << 8));
    }

    /**
     * Подключается к серверу
     *
     * @param host домен сервера
     * @param port порт сервера
     * @throws IOException ошибка подключения
     */
    private void connect(String host, int port) throws IOException {
        cliUserInterface.writeln("===============\nConnecting to the server...\n Host: " + host + "\n Port: " + port);
        socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        socketChannel.configureBlocking(false);
        sendData(new String(SystemCommand.IS_READY)); // спрашиваем сервера, готов ли он нас обслужить
        String serverAnswer;
        int animationSlide = 0;
        do {
            animationSlide++;
            animationSlide = animationSlide % WAITING_ANIMATION.length;
            cliUserInterface.write("\rОжидаем ответа от сервера " + WAITING_ANIMATION[animationSlide] + '\n');
            serverAnswer = readData();
        } while (!SystemCommand.equals(serverAnswer.getBytes(), SystemCommand.READY));
        cliUserInterface.writeln("Успешно подключился к серверу. Начинаем работу.!\n===============");
    }

    /**
     * Отправляет строку серверу.
     *
     * @param data строка, которая будет отправлена
     * @throws IOException
     */
    private void sendData(String data) throws IOException {
        networkUserInterface.write(data);
    }

    private String readData() throws IOException {
        return networkUserInterface.read();
    }

    private String getStringFormBuffer(ByteBuffer buffer) {
        return new String(buffer.array());
    }

    private void disconnect() throws IOException {
        socketChannel.close();
    }

    private void checkClosedSession(byte[] b) throws IOException {
        if (SystemCommand.equals(b, SystemCommand.CLOSE_SESSION)) {
            log.debug("Пришёл сигнал об окончании сессии. Завершаем работу.");
            disconnect();
            cliUserInterface.writeln("Завершаем работу.");
            System.exit(0);
        }
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
                userInput = cliUserInterface.readWithMessage("#-> ", true);
                if ("qeq".equals(userInput)) {
                    System.out.println(getStringFormBuffer(buffer));
                    continue;
                }
                sendData(userInput);
                Thread.sleep(10);
                String serverAnswer = readData();
                checkClosedSession(serverAnswer.getBytes());
                System.out.println("----------------\n" + serverAnswer + "\n--------------");
            }
        } catch (IOException | InterruptedException e) {
            log.error("Failed to connect!");
            e.printStackTrace();
        }
    }


}
