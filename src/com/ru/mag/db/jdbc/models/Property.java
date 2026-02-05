package com.ru.mag.db.jdbc.models;

public class Property {
    private int id;
    private double price;
    private int squareMeters;
    private String location;
    private String type;
    private int ownerId;

    /**
     * create type location_t as object ( latitude varchar2(10), longtitude varchar2(10), city varchar2(20) );
     * @param id
     * @param price
     * @param squareMeters
     * @param location
     * @param type
     * @param ownerId
     */

    public Property(int id, double price, int squareMeters, String location, String type, int ownerId) {
        this.id = id;
        this.price = price;
        this.squareMeters = squareMeters;
        this.location = location;
        this.type = type;
        this.ownerId = ownerId;
    }

    public Property(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
