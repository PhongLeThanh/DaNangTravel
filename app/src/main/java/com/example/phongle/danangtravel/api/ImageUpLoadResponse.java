package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.ImageUpload;
import com.google.gson.annotations.SerializedName;

public class ImageUpLoadResponse {
    @SerializedName("data")
    private ImageUpload data;

    public ImageUpload getData() {
        return data;
    }

    public void setData(ImageUpload data) {
        this.data = data;
    }
}
