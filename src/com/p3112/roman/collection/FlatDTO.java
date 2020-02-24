package com.p3112.roman.collection;
// Created by Roman Devyatilov (Fr1m3n) in 17:22 11.02.2020


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.p3112.roman.exceptions.InvalidInputException;
import com.p3112.roman.utils.UserInterface;
import lombok.NonNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Data transfer object для квартиры.
 */
@JsonAutoDetect
public class FlatDTO {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    @JsonDeserialize(as = Coordinates.class)
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long area; //Значение поля должно быть больше 0
    private long numberOfRooms; //Максимальное значение поля: 18, Значение поля должно быть больше 0
    private Long livingSpace; //Значение поля должно быть больше 0
    private boolean new1;
    private View view; //Поле может быть null
    @JsonDeserialize(as = House.class)
    private House house; //Поле может быть null

    public FlatDTO() {
    }

    public FlatDTO(Flat flat) {
        id = flat.getId();
        name = flat.getName();
        coordinates = flat.getCoordinates();
        String creationDateString = flat.getCreationDate();
        creationDate = (creationDateString == null) ? ZonedDateTime.now() : ZonedDateTime.parse(creationDateString);
        area = flat.getArea();
        numberOfRooms = flat.getNumberOfRooms();
        livingSpace = flat.getLivingSpace();
        new1 = flat.isNew1();
        view = flat.getView();
        house = flat.getHouse();
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

    public void setCreationDate(@NonNull String creationDate) {
        this.creationDate = ZonedDateTime.parse(creationDate);
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

    public void setNew1(@NonNull boolean new1) {
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

    public void setHouse(House house) {
        this.house = house;
    }

    @JsonIgnore
    public Flat toFlat() {
        Flat flat = new Flat(id,
                name,
                coordinates,
                creationDate,
                area,
                numberOfRooms,
                livingSpace,
                new1,
                view,
                house
        );
        return flat;
    }

    public void checkFields() {
        if (name.isEmpty()) {
            throw new InvalidInputException("Name пустая.");
        }

        if (!UserInterface.checkNumber(area, 0, -1) ||
        !UserInterface.checkNumber(numberOfRooms, 0, 18) ||
        !UserInterface.checkNumber(livingSpace, 0, -1)) {
            throw new InvalidInputException("Одно из чисел во входном json не подходит под ограничения. (Flat)");
        }
        if (house != null) {
            if (!UserInterface.checkNumber(house.getNumberOfFloors(), 0, -1) ||
            !UserInterface.checkNumber(house.getYear(), 0, -1)) {
                throw new InvalidInputException("Одно из чисел во входном json не подходит под ограничения. (House)");
            }
        }
    }
}
