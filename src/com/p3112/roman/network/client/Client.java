package com.p3112.roman.network.client;
// Created by Roman Devyatilov (Fr1m3n) in 20:58 08.03.2020


import com.p3112.roman.CustomRunnable;
import com.p3112.roman.exceptions.InvalidArgs;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class Client implements CustomRunnable {

    private SocketChannel socketChannel;
    private ByteBuffer buffer;
    private UserInterfaceImpl cliUserInterface;
    private UserInterfaceImpl networkUserInterface;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ByteArrayInputStream byteArrayInputStream;


    public Client() {
        buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        cliUserInterface = new UserInterfaceCLIImpl(System.in, System.out, true);
        byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        byteArrayOutputStream = new ByteArrayOutputStream();

        networkUserInterface = new UserInterfaceImpl(byteArrayInputStream, byteArrayOutputStream, true) {

            @Override
            public void write(String message) throws IOException {
                if (message == null) {
                    log.debug("Пришла null-строка");
                    message = "NULL";
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
                            log.debug("Принял {} байт данных: \n {}", receivedBytesCount, CollectionUtils.writeHexBytes(buffer.array()));
                            temp = CollectionUtils.concatByteArrays(temp, Arrays.copyOf(buffer.array(), receivedBytesCount));
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
                } catch (EOFException e) {
                    log.error("EOFException поймана. Пришёл битый пакет.");
                    return null;
                }
            }
        };
    }


    // для тестов
    public static void main(String[] args) {
        CustomRunnable client = new Client();
        client.run(args);
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
        cliUserInterface.write("Ожидаем ответа от сервера \n");
        serverAnswer = readData();
        if (!SystemCommand.equals(serverAnswer.getBytes(), SystemCommand.READY)) {
            cliUserInterface.writeln("Сервер не ответил на готовность. Завершаем работу.");
            System.exit(1);
        }
        cliUserInterface.writeln("Успешно подключился к серверу. Начинаем работу.!\n===============");
    }

    private void connect(String[] args) throws IOException {
        if (args.length >= 2) {
            connect(args[0], Integer.parseInt(args[1]));
        } else if (args.length == 1) {
            String[] hostAndPort = args[0].split(":");
            if (hostAndPort.length != 2) {
                throw new InvalidArgs();
            }
            connect(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
        } else {
            connect("localhost", 5858);
        }
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

    private boolean checkForSystemCommand(byte[] b) throws IOException {
        if (!SystemCommand.isServiceCommand(b)) {
            return false;
        }
        checkClosedSession(b);
        return checkFileRequest(b);
    }

    private void checkClosedSession(byte[] b) throws IOException {
        if (SystemCommand.equals(b, SystemCommand.CLOSE_SESSION)) {
            log.debug("Пришёл сигнал об окончании сессии. Завершаем работу.");
            disconnect();
            cliUserInterface.writeln("Завершаем работу.");
            System.exit(0);
        }
    }

    private boolean checkFileRequest(byte[] b) throws IOException {
        String fileName = SystemCommand.parseFileRequestCommand(b);
        if (fileName == null) {
            return false;
        }
        Path pathToFile = Paths.get(fileName);

        log.debug("Запрошен файл: {}", pathToFile.toAbsolutePath());
        Scanner scanner = new Scanner(new FileInputStream(pathToFile.toFile()));
        log.debug("Начинаю исполнение файла.");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            sendData(line);
            cliUserInterface.writeln(readData());
        }
        log.debug("Файл закончился");
        return true;
    }


    private void mainLoop() throws IOException, InterruptedException {
        String userInput;

        while (socketChannel.isConnected()) {
            userInput = cliUserInterface.readWithMessage("#-> ", true);
            if ("qeq".equals(userInput)) {
                System.out.println(getStringFormBuffer(buffer));
                continue;
            }
            sendData(userInput);
            Thread.sleep(10);
            String serverAnswer = readData();
            if (serverAnswer != null) {
                if (!checkForSystemCommand(serverAnswer.getBytes())) {
                    cliUserInterface.writeln(serverAnswer);
                }
            } else {
                cliUserInterface.writeln("Произошла какая-то ошибка на стороне сервера. Пришёл \"битый\" пакет.");
            }
        }
    }


    /**
     * Точка входа для клиента.
     */
    @Override
    public void run(String[] args) {
        do {
            try {
                connect(args);
            } catch (IOException e) {
                log.error("Не удалось подключиться. Пробуем ещё раз через 5 секунд...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    log.error("Это исключение не должно было быть выкинуто." +
                            " Но, что-то пошло не так." +
                            " Помните - меня писал дурачок..." +
                            " Смотрите в стекстрейс и смейтесь над ним......");
                    e.printStackTrace();
                }
            }
        } while (socketChannel == null || !socketChannel.isConnected());
        try {
            mainLoop();
        } catch (IOException e) {
            log.error("");
            e.printStackTrace();
        } catch (InterruptedException e) {
            log.error("Это исключение не должно было быть выкинуто." +
                    " Но, что-то пошло не так." +
                    " Помните - меня писал дурачок..." +
                    " Смотрите в стекстрейс и смейтесь над ним......");
            e.printStackTrace();
        }
    }
}
