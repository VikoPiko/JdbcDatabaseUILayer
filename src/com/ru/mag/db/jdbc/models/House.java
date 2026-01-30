package com.ru.mag.db.jdbc.models;

public class House {
    private int propertyId;
    private int numberOfFloors;
    private int gardenSize;
    private int bathrooms;
    private int rooms;

    public int getPropertyId() { return propertyId; }
    public void setPropertyId(int propertyId) { this.propertyId = propertyId; }

    public int getNumberOfFloors() { return numberOfFloors; }
    public void setNumberOfFloors(int numberOfFloors) { this.numberOfFloors = numberOfFloors; }

    public int getGardenSize() { return gardenSize; }
    public void setGardenSize(int gardenSize) { this.gardenSize = gardenSize; }

    public int getBathrooms() { return bathrooms; }
    public void setBathrooms(int bathrooms) { this.bathrooms = bathrooms; }

    public int getRooms() { return rooms; }
    public void setRooms(int rooms) { this.rooms = rooms; }
}
