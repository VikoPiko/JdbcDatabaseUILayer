package com.ru.mag.db.jdbc.models;

public class Apartment {
    private int propertyId;
    private int rooms;
    private int bathrooms;

    public Apartment() {}

    public Apartment(int propertyId, int rooms, int bathrooms) {
        this.propertyId = propertyId;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }
}
