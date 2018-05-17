package com.example.phongle.danangtravel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeEvent {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("eventId")
    @Expose
    private int eventId;

    public LikeEvent(int id, int userId, int eventId) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
    }

    public LikeEvent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
