package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventResponse {
    @SerializedName("data")
    @Expose
    private List<Event> data = null;

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }
}
