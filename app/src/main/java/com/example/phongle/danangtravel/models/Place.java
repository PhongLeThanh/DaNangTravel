package com.example.phongle.danangtravel.models;

public class Place {
    private String Name;
    private int Image;
    private int Rating;
    private int NumComment;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public int getNumComment() {
        return NumComment;
    }

    public void setNumComment(int numComment) {
        NumComment = numComment;
    }

    public Place(String name, int image, int rating, int numComment) {
        Name = name;
        Image = image;
        Rating = rating;
        NumComment = numComment;
    }
}
