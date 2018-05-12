package com.example.phongle.danangtravel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hotel extends Place {
    @SerializedName("cost")
    @Expose
    private float Cost;
    @SerializedName("website")
    @Expose
    private String Website;
    @SerializedName("moreinformation")
    @Expose
    private String MoreInformation;

    public float getCost() {
        return Cost;
    }

    public void setCost(float cost) {
        Cost = cost;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getMoreInformation() {
        return MoreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        MoreInformation = moreInformation;
    }

    public Hotel(Integer id, Integer categoryId, Integer locationId, String placeName, String description, String detail, String address, String phone, double latitude, double longitude, Integer rating, Double distance, String numComment, Restaurant restaurant, TouristAttraction touristattraction, Hotel hotel, List<Comment> comments, List<Image> images, float cost, String website, String moreInformation) {
        super(id, categoryId, locationId, placeName, description, detail, address, phone, latitude, longitude, rating, distance, numComment, restaurant, touristattraction, hotel, comments, images);
        Cost = cost;
        Website = website;
        MoreInformation = moreInformation;
    }

    public Hotel(float cost, String website, String moreInformation) {
        Cost = cost;
        Website = website;
        MoreInformation = moreInformation;
    }
    public void setPlace(Place place){
        setId(place.getId());
        setLocationId(place.getLocationId());
        setCategoryId(place.getCategoryId());
        setPlaceName(place.getPlaceName());
        setDescription(place.getDescription());
        setDetail(place.getDetail());
        setAddress(place.getAddress());
        setPhone(place.getPhone());
        setLatitude(place.getLatitude());
        setLongitude(place.getLongitude());
        setRating(place.getRating());
        setNumcomment(place.getNumcomment());
        setImages(place.getImages());
    }
}
