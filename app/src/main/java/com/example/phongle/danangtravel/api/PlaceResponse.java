package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.Place;
import com.example.phongle.danangtravel.models.PlaceNew;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceResponse {
    @SerializedName("data")
    @Expose
    private List<Place> data = null;

    public List<Place> getData() {
        return data;
    }

    public void setData(List<Place> data) {
        this.data = data;
    }
}
