package com.example.lab8;

public class Payment {

    public String timestamp;
    private double cost;
    private String name;
    private String type;

    public Payment() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Payment(double cost, String name, String type, String timestamp) {
        this.cost = cost;
        this.name = name;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
