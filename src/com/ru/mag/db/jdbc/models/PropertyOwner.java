package com.ru.mag.db.jdbc.models;

public class PropertyOwner {
    private int id;
    private int personId;

    public PropertyOwner(int id, int personId) {
        this.id = id;
        this.personId = personId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
