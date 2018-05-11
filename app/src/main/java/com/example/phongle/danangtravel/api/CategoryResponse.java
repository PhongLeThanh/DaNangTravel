package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.Category;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {
    @SerializedName("data")
    @Expose
    private List<Category> data = null;

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
