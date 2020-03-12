package com.p3112.roman.collection;
// Writed by Roman Devyatilov (Fr1m3n) in 9:34 07.02.2020


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NonNull;

import java.time.ZonedDateTime;


/**
 * Модель квартиры
 */
public class Flat implements Comparable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long area; //Значение поля должно быть больше 0
    private long numberOfRooms; //Максимальное значение поля: 18, Значение поля должно быть больше 0
    private Long livingSpace; //Значение поля должно быть больше 0
    private boolean new1;
    private View view; //Поле может быть null
    private House house; //Поле может быть null

    public Flat(String name, Coordinates coordinates, long area, long numberOfRooms, Long livingSpace, boolean new1, View view, House house) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.area = area;
        assert area > 0;
        this.numberOfRooms = numberOfRooms;
        assert numberOfRooms > 0 && numberOfRooms <= 18;
        this.livingSpace = livingSpace;
        assert livingSpace > 0;
        this.new1 = new1;
        this.view = view;
        this.house = house;
    }

    @JsonCreator
    public Flat(long id,
                String name,
                Coordinates coordinates,
                ZonedDateTime creationDate,
                long area,
                long numberOfRooms,
                Long livingSpace,
                boolean new1,
                View view,
                House house) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.livingSpace = livingSpace;
        this.new1 = new1;
        this.view = view;
        this.house = house;
    }


    public Flat() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@NonNull Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCreationDate() {
        return creationDate.toString();
    }

    public void setCreationDate(String creationDate) {
        if (creationDate != null) {
            this.creationDate = ZonedDateTime.parse(creationDate);
        } else {
            this.creationDate = ZonedDateTime.now();
        }
    }

    public long getArea() {
        return area;
    }

    public void setArea(long area) {
        this.area = area;
    }

    public long getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Long getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(@NonNull Long livingSpace) {
        this.livingSpace = livingSpace;
    }

    public boolean isNew1() {
        return new1;
    }

    public void setNew1(boolean new1) {
        this.new1 = new1;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public House getHouse() {
        return house;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "\n id=" + id +
                "\n name='" + name + '\'' +
                "\n coordinates=" + coordinates +
                "\n creationDate=" + creationDate +
                "\n area=" + area +
                "\n numberOfRooms=" + numberOfRooms +
                "\n livingSpace=" + livingSpace +
                "\n new1=" + new1 +
                "\n view=" + view +
                "\n house=" + house +
                '}';
    }

    public void setHouse(House house) {
        this.house = house;
    }


    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof Flat)) {
            throw new ClassCastException();
        }
        Flat fo = (Flat) o;
        if (this.getArea() == fo.getArea()) {
            return 0;
        } else {
            return this.getArea() < fo.getArea() ? -1 : 1;
        }
    }

}
