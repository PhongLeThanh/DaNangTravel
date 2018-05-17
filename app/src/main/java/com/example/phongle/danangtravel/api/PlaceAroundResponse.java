package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.PlaceAround;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceAroundResponse {
    @SerializedName("data")
    @Expose
    private List<PlaceAround> data = null;

    public List<PlaceAround> getData() {
        return data;
    }

    public void setData(List<PlaceAround> data) {
        this.data = data;
    }
}
