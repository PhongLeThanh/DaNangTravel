package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.LikePlace;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikePlaceResponse {
    @SerializedName("data")
    @Expose
    private LikePlace data = null;

    public LikePlace getData() {
        return data;
    }

    public void setData(LikePlace data) {
        this.data = data;
    }
}
