package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.Location;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationResponse {
    @SerializedName("data")
    @Expose
    private List<Location> data = null;

    public List<Location> getData() {
        return data;
    }

    public void setData(List<Location> data) {
        this.data = data;
    }
}
