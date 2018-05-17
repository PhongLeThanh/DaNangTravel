package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.Comment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectCommentResponse {
    @SerializedName("data")
    @Expose
    private Comment data = null;

    public Comment getData() {
        return data;
    }

    public void setData(Comment data) {
        this.data = data;
    }
}
