package com.p3112.roman.collection;
// Writed by Roman Devyatilov (Fr1m3n) in 9:14 07.02.2020


import java.util.*;


/**
 * Реализация Storage. Хранит квартиры в стэке.
 */
public class StackFlatStorageImpl implements Storage<Flat> {
    private Stack<Flat> flats = new Stack<>();
    private Date creationDate;
    private Set<Long> idSet = new HashSet<>();
    private long maxId = 0;

    /**
     * Конструктор, который инициализирует время создания объекта.
     */
    public StackFlatStorageImpl() {
        creationDate = new Date();
    }


    public Stack<Flat> getFlats() {
        return flats;
    }

    public void setFlats(Stack<Flat> flats) {
        this.flats = flats;
    }

    /**
     * Метод, добавляющий квартиру в стэк. Гарантирует уникальность идентификатора квартиры.
     *
     * @param obj объект квартиры
     */
    @Override
    public void put(Flat obj) {
        while (idSet.contains(obj.getId())) {
            obj.setId(maxId);
            maxId = Math.max(maxId, obj.getId() + 1);
        }
        flats.push(obj);
        idSet.add(obj.getId());
    }


    /**
     * Добавляет квартиру в стэк на определённую позицию.
     *
     * @param index позиция, в которую добавится квартира
     * @param obj   квартира, которая будет добавлена в стэк
     */
    @Override
    public void put(int index, Flat obj) {
        while (idSet.contains(obj.getId())) {
            obj.setId(maxId);
            maxId = Math.max(maxId, obj.getId() + 1);
        }
        flats.add(index, obj);
        idSet.add(obj.getId());

    }


    /**
     * Метод, возращающий квартиру по индексу в стэке.
     *
     * @param index индекс, по которому будет возвращена квартира
     * @return объект класса Flat, который лежал в стэке по индексу index
     */
    @Override
    public Flat get(int index) {
        return flats.get(index);
    }


    /**
     * Возарщает квартиру с верхушки стэка.
     *
     * @return квартира с верхушки стэка
     */
    @Override
    public Flat get() {
        return flats.peek();
    }

    /**
     * Удаляет объект из стэка.
     *
     * @param obj объект, который нужно удалить
     */
    @Override
    public void remove(Flat obj) {
        flats.remove(obj);
    }

    /**
     * Возарщает кол-во квартир в стэке.
     *
     * @return кол-во элементов в стэке.
     */
    @Override
    public int size() {
        return flats.size();
    }

    /**
     * Очищает стэк.
     */
    @Override
    public void clear() {
        flats.clear();
    }

    /**
     * Преобразовывает коллекцию в List.
     *
     * @return List, с элементами из стэка
     */
    @Override
    public List<Flat> toList() {
        return flats;
    }

    @Override
    public Date getInitializationTime() {
        return creationDate;
    }

    @Override
    public Class<?> getCollectionClass() {
        return flats.getClass();
    }

    @Override
    public void setCollection(Collection<Flat> collection) {
        flats = (Stack<Flat>) collection;
    }

    @Override
    public long getMaximumId() {
        return maxId;
    }


}
