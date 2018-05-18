package com.example.phongle.danangtravel.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateProfileResponse {
    @SerializedName("data")
    private List<Integer> result;

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }
}
