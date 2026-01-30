package com.ru.mag.db.jdbc.models;

import java.util.Date;

public class Agent extends Person {
    private int id;
    private Double salary;
    private Date hireDate;

    public Agent() {}

    public Agent(int id, Double salary, Date hireDate) {
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
