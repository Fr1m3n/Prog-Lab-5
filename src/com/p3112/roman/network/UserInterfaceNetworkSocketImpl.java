package com.p3112.roman.network;
// Created by Roman Devyatilov (Fr1m3n) in 17:40 01.04.2020


import com.p3112.roman.network.Request;
import com.p3112.roman.network.SystemCommand;
import com.p3112.roman.utils.CollectionUtils;
import com.p3112.roman.utils.CustomByteInputStream;
import com.p3112.roman.utils.UserInterfaceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class UserInterfaceNetworkSocketImpl extends UserInterfaceImpl {
    private ObjectInputStream objectInputStream;
    private ByteArrayOutputStream outputByteStream;
    private CustomByteInputStream inputByteStream;
    private ObjectOutputStream objectOutputStream;
    private byte[] buffer = new byte[2048];


    public UserInterfaceNetworkSocketImpl(InputStream inputStream, OutputStream outputStream, boolean interactive) throws IOException {
        super(inputStream, outputStream, interactive);
    }


    @Override
    public void write(String message) throws IOException {
        write(message.getBytes());
    }

    @Override
    public void write(byte[] data) throws IOException {
        Request request = new Request(data);
        log.debug("Отправляю Request с содержимым: {}", request.getContentByString());
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(request);
    }


    @Override
    public String read() throws IOException {
        try {
            try {
                ObjectInputStream ois = new ObjectInputStream(is);
                log.debug("Прочитал объект.");
                Request request = (Request) ois.readObject();
                return request.getContentByString();
            } catch (StreamCorruptedException e) {
                log.debug("Не удалось прочитать объект, работаю с массивом байт.");
                return new String(is.readAllBytes());
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new IOException();
        }
    }
}
