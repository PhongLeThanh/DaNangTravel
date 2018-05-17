package com.example.phongle.danangtravel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceAround {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("locationId")
    @Expose
    private Integer locationId;
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
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("numcomment")
    @Expose
    private String numcomment;
    @SerializedName("picture")
    @Expose
    private String picture;

    public PlaceAround() {
    }

    public PlaceAround(Integer id, Integer categoryId, Integer locationId, String placeName, String description, String detail, String address, String phone, double latitude, double longitude, Integer rating, Double distance, String numcomment, String picture) {

        this.id = id;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.placeName = placeName;
        this.description = description;
        this.detail = detail;
        this.address = address;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.distance = distance;
        this.numcomment = numcomment;
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getNumcomment() {
        return numcomment;
    }

    public void setNumcomment(String numcomment) {
        this.numcomment = numcomment;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
