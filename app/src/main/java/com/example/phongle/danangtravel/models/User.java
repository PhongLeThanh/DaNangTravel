package com.example.phongle.danangtravel.models;

public class User {
    private String username;
    private int avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public User(String username, int avatar) {
        this.username = username;
        this.avatar = avatar;
    }
}
