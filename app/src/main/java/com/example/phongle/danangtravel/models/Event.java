package com.example.phongle.danangtravel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Event {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("finish")
    @Expose
    private String finish;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("numlike")
    @Expose
    private String numlike;
    @SerializedName("likeevents")
    @Expose
    private List<LikeEvent> likeevents = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Event(Integer id, String eventName, String detail, String start, String finish, String address, String latitude, String longitude, String image, String numlike, List<LikeEvent> likeevents, String createdAt, String updatedAt) {
        this.id = id;
        this.eventName = eventName;
        this.detail = detail;
        this.start = start;
        this.finish = finish;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.numlike = numlike;
        this.likeevents = likeevents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Event() {
    }

    public String getNumlike() {
        return numlike;
    }

    public void setNumlike(String numlike) {
        this.numlike = numlike;
    }

    public List<LikeEvent> getLikeevents() {
        return likeevents;
    }

    public void setLikeevents(List<LikeEvent> likeevents) {
        this.likeevents = likeevents;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
