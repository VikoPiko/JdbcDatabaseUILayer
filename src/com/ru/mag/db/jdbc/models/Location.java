package com.ru.mag.db.jdbc.models;

public class Location {
    private String latitude;
    private String longitude;
    private String city;

    public Location(String latitude, String longitude, String city) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    public String getLatitude() { return latitude; }
    public String getLongitude() { return longitude; }
    public String getCity() { return city; }
}
