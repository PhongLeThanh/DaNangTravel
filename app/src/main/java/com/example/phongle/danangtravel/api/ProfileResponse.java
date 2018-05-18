package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.Profile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("data")
    @Expose
    private Profile data = null;

    public Profile getData() {
        return data;
    }

    public void setData(Profile data) {
        this.data = data;
    }
}
