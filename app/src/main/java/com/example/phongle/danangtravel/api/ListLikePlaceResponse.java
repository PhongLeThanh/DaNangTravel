package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.LikePlace;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListLikePlaceResponse {
    @SerializedName("data")
    @Expose
    private List<LikePlace> data = null;

    public List<LikePlace> getData() {
        return data;
    }

    public void setData(List<LikePlace> data) {
        this.data = data;
    }
}
