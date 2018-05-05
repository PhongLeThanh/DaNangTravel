package com.example.phongle.danangtravel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceNew {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("locationId")
    @Expose
    private Integer locationId;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("placeName")
    @Expose
    private String placeName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("waypoint")
    @Expose
    private String waypoint;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("numComment")
    @Expose
    private String numComment;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(String waypoint) {
        this.waypoint = waypoint;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNumComment() {
        return numComment;
    }

    public void setNumComment(String numComment) {
        this.numComment = numComment;
    }

    public PlaceNew(Integer id, Integer locationId, Integer categoryId, String placeName, String description, String detail, String address, String phone, String waypoint, Integer rating, String numComment, String createdAt, String updatedAt) {
        this.id = id;
        this.locationId = locationId;
        this.categoryId = categoryId;
        this.placeName = placeName;
        this.description = description;
        this.detail = detail;
        this.address = address;
        this.phone = phone;
        this.waypoint = waypoint;
        this.rating = rating;
        this.numComment = numComment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PlaceNew() {
    }
}
