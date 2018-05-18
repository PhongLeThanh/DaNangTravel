package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.LikeEvent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeEventResponse {
    @SerializedName("data")
    @Expose
    private LikeEvent data = null;

    public LikeEvent getData() {
        return data;
    }

    public void setData(LikeEvent data) {
        this.data = data;
    }
}
