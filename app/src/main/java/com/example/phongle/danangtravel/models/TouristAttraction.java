package com.example.phongle.danangtravel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TouristAttraction extends Place {
    @SerializedName("ticket")
    @Expose
    private Object ticket;
    @SerializedName("moreInformation")
    @Expose
    private String moreInformation;

    public Object getTicket() {
        return ticket;
    }

    public void setTicket(Object ticket) {
        this.ticket = ticket;
    }

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        this.moreInformation = moreInformation;
    }

    public TouristAttraction(Integer id, Integer categoryId, Integer locationId, String placeName, String description, String detail, String address, String phone, String waypoint, Integer rating, String numComment, Restaurant restaurant, TouristAttraction touristattraction, Hotel hotel, List<Image> images, Object ticket, String moreInformation) {
        super(id, categoryId, locationId, placeName, description, detail, address, phone, waypoint, rating, numComment, restaurant, touristattraction, hotel, images);
        this.ticket = ticket;
        this.moreInformation = moreInformation;
    }

    public TouristAttraction(Object ticket, String moreInformation) {
        this.ticket = ticket;
        this.moreInformation = moreInformation;
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
        setWaypoint(place.getWaypoint());
        setRating(place.getRating());
        setNumComment(place.getNumComment());
    }
}
