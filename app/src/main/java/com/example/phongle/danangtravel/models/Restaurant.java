package com.example.phongle.danangtravel.models;

public class Restaurant extends Place {
    private float Cost;

    public Restaurant(String name, int image, int rating, int numComment, float cost) {
        super(name, image, rating, numComment);
        Cost = cost;
    }

    public float getCost() {
        return Cost;
    }

    public void setCost(float cost) {
        Cost = cost;
    }
}
