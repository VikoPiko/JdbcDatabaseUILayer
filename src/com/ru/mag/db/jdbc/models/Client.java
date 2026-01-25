package com.ru.mag.db.jdbc.models;

public class Client extends Person{
    private double budget;
    private String areaInterestedIn;

    public Client(){}

    public Client(double Budget, String areaInterestedIn){
        this.budget = Budget;
        this.areaInterestedIn = areaInterestedIn;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getAreaInterestedIn() {
        return areaInterestedIn;
    }

    public void setAreaInterestedIn(String areaInterestedIn) {
        this.areaInterestedIn = areaInterestedIn;
    }
}
