package com.example.phongle.danangtravel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Restaurant extends Place {
    @SerializedName("website")
    @Expose
    private String Website;
    @SerializedName("time")
    @Expose
    private String Time;
    @SerializedName("moreinformation")
    @Expose
    private String MoreInformation;

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getMoreInformation() {
        return MoreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        MoreInformation = moreInformation;
    }

    public Restaurant(Integer id, Integer categoryId, Integer locationId, String placeName, String description, String detail, String address, String phone, double latitude, double longitude, Integer rating, Double distance, String numComment, Restaurant restaurant, TouristAttraction touristattraction, Hotel hotel, List<Comment> comments, List<Image> images, String website, String time, String moreInformation) {
        super(id, categoryId, locationId, placeName, description, detail, address, phone, latitude, longitude, rating, distance, numComment, restaurant, touristattraction, hotel, comments, images);
        Website = website;
        Time = time;
        MoreInformation = moreInformation;
    }

    public Restaurant(String website, String time, String moreInformation) {
        Website = website;
        Time = time;
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
    }
}
