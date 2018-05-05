package com.example.phongle.danangtravel.api;

import com.example.phongle.danangtravel.models.Location;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TravelService {
    @GET("locations")
    Call<LocationResponse> getLocations();

    @GET("places")
    Call<PlaceResponse> getPlace();

    @GET("places/viewTop")
    Call<PlaceResponse> getTopPlace();

    @GET("places/viewByCategory/{id}")
    Call<PlaceResponse> getPlaceCategory(@Path("id") int id);
}
