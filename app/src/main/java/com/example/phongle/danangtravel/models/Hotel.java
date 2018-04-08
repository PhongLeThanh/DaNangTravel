package com.example.phongle.danangtravel.models;

public class Hotel extends Place {
    private float Cost;
    public float getCost() {
        return Cost;
    }

    public void setCost(float cost) {
        Cost = cost;
    }

    public Hotel(String name, int image, int rating, int numComment, float cost) {
        super(name, image, rating, numComment);
        Cost = cost;
    }
}
