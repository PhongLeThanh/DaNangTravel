package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.Comment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentResponse {
    @SerializedName("data")
    @Expose
    private List<Comment> data = null;

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }
}
