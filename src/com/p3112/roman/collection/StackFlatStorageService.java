package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 15:03 08.02.2020


import com.p3112.roman.utils.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, реализующий сервис для коллекции StackFlatStorageImpl
 */
@Slf4j
public class StackFlatStorageService implements StorageService {
    private static Storage<Flat> st;

    /**
     * Конструктор, который инициализирует коллекцию с которой работать.
     *
     * @param st коллекция, с которой будет работать сервис
     */
    public StackFlatStorageService(Storage<Flat> st) {
        StackFlatStorageService.st = st;
    }

    /**
     * Возвращает информацию о коллекицц.
     *
     * @return строка с информацией о коллекции
     */
    public String info() {
        StringBuilder sb = new StringBuilder();
        sb.append("Время инициализации коллекции: ").append(st.getInitializationTime().toString()).append('\n');
        sb.append("Количество элементов в коллекции: ").append(st.size()).append('\n');
        sb.append("Тип коллекции: ").append(st.getCollectionClass().getName());
        return sb.toString();
    }

    /**
     * Добавляет квартиру в коллекцию.
     *
     * @param flat квартира, которая будет добавлена.
     */
    @Override
    public void add(Flat flat) {
        st.put(flat);
    }


    /**
     * Добавляет квартиру, если она меньше самой наименьшей квартиры из коллекции.
     *
     * @param flat квартира, которая будет добавлена
     * @return true - если квартира добавлена успешно, false - в ином случае
     */
    @Override
    public boolean addIfMin(Flat flat) {
        List<Flat> flats = st.toList().stream().sorted().collect(Collectors.toList());
        if (flats.isEmpty()) {
            return false;
        }
        Flat firstFlat = flats.get(0);
        if (firstFlat.compareTo(flat) > 0) {
            add(flat);
            log.info("Элемент {} добавлен успешно", flat);
            return true;
        } else {
            log.info("Элемент оказался больше минимального.");
            return false;
        }
    }

    /**
     * Очищает коллекцию.
     */
    @Override
    public void clear() {
        st.clear();
    }

    /**
     * Возвращает кол-во квартир, у которых значение house меньше данного.
     *
     * @param house данный пользователем объект класса House
     * @return кол-во квартир, которые удовлетворяют условию
     */
    @Override
    public long countGreaterThanHouse(House house) {
        return st.toList().stream().filter(x -> x.getHouse().compareTo(house) < 0).count();

    }

    /**
     * Возвращает список всех квартир, у которых кол-во комнат меньше данного.
     *
     * @param numOfRooms сравниваемое значение кол-ва квартир
     * @return список квартир, удовлетворяющих условию
     */
    @Override
    public List<Flat> filterLessThanNumberOfRooms(long numOfRooms) {
        return st.toList().stream().filter(x -> x.getNumberOfRooms() < numOfRooms).collect(Collectors.toList());
    }

    /**
     * Помещает квартиру в коллекцию на определённую позицию.
     *
     * @param ind  позиция, в которую необходимо поместить квартиру
     * @param flat квартиру, которую необходимо поместить
     */
    @Override
    public void insertAt(int ind, Flat flat) {
        st.put(ind, flat);
    }

    /**
     * Удаляет квартиру из коллекции по позиции.
     *
     * @param ind позиция по которой необходимо удалить квартиру.
     * @return true - если квартира удалена успешно, false - в ином случае.
     */
    @Override
    public boolean removeAt(int ind) {
        if (st.size() <= ind) {
            return false;
        }
        st.remove(st.get(ind));
        return true;
    }

    /**
     * Удаляет квартиру по индентификатору.
     *
     * @param id идентификатор квартиры
     * @return true - если квартира с данный id существовала и была удалена, false - в ином случае.
     */
    @Override
    public boolean removeById(long id) {
        List<Flat> flatsToDelete = st.toList().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if (flatsToDelete.isEmpty()) {
            return false;
        }
        flatsToDelete.forEach(st::remove);
        return true;
    }

    /**
     * Возвращает все элементы коллекции.
     *
     * @return Строку со всеми элементами коллекции.
     */
    @Override
    public String show() {
        StringBuilder sb = new StringBuilder("Элементов в коллекции: " + st.size()).append("\n");
        st.toList().forEach(x -> sb.append(x.toString()).append('\n'));
        return sb.toString();
    }

    /**
     * Изменяет квартиру с заданным индентификатором.
     *
     * @param id   идентификатор квартиры
     * @param flat объект, который будет вместо старого значения
     * @return true - если объект был найден и обновлён, false -  в ином случае.
     */
    @Override
    public boolean update(long id, Flat flat) {
        if (!removeById(id)) {
            return false;
        }
        flat.setId(id);
        st.put(flat);
        return true;
    }

    /**
     * Возвращает размер коллекции.
     *
     * @return кол-во элементов в коллекции.
     */
    @Override
    public int size() {
        return st.size();
    }

    /**
     * Сохраняет коллекцию в файл, в формате JSON
     *
     * @param pathToFile путь до файла, который будет помещён JSON
     * @throws IOException В случае, если ввод/вывод был неудачен.
     */
    @Override
    public void save(String pathToFile) throws IOException {
        JsonWriter jsonWriter = new JsonWriter();
        try {
            FlatDTO[] flats = new FlatDTO[st.size()];
            st.toList().stream().map(FlatDTO::new).collect(Collectors.toList()).toArray(flats);
            jsonWriter.writeCollectionToFile(flats, pathToFile);
        } catch (IOException e) {
            log.error("Ошибка во время сохранения коллекции в файл. {}", e.getMessage());
            throw e;
        }
    }
}
