package com.example.phongle.danangtravel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Place {
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
    @SerializedName("waypoint")
    @Expose
    private String waypoint;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("numComment")
    @Expose
    private String numComment;
    @SerializedName("restaurant")
    @Expose
    private Restaurant restaurant;
    @SerializedName("touristattraction")
    @Expose
    private TouristAttraction touristattraction;
    @SerializedName("hotel")
    @Expose
    private Hotel hotel;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

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

    public String getNumComment() {
        return numComment;
    }

    public void setNumComment(String numComment) {
        this.numComment = numComment;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public TouristAttraction getTouristattraction() {
        return touristattraction;
    }

    public void setTouristattraction(TouristAttraction touristattraction) {
        this.touristattraction = touristattraction;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Place() {
    }

    public Place(Integer id, Integer categoryId, Integer locationId, String placeName, String description, String detail, String address, String phone, String waypoint, Integer rating, String numComment, Restaurant restaurant, TouristAttraction touristattraction, Hotel hotel, List<Image> images) {
        this.id = id;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.placeName = placeName;
        this.description = description;
        this.detail = detail;
        this.address = address;
        this.phone = phone;
        this.waypoint = waypoint;
        this.rating = rating;
        this.numComment = numComment;
        this.restaurant = restaurant;
        this.touristattraction = touristattraction;
        this.hotel = hotel;
        this.images = images;
    }

    public String toString() {
        return (new StringBuilder()
                .append(id)
                .append("---")
                .append(categoryId)
                .append("---")
                .append(placeName))
                .toString();
    }
}
