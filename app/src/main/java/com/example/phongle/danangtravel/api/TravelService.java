package com.example.phongle.danangtravel.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TravelService {
    @GET("locations")
    Call<LocationResponse> getLocations();

    @GET("places")
    Call<PlaceResponse> getPlace();

    @GET("places/viewTop")
    Call<PlaceResponse> getTopPlace();

    @GET("places/viewTopInLocation/{locationId}")
    Call<PlaceResponse> getTopPlaceInLocation(@Path("locationId") int id);

    @GET("places/viewByCategory/{categoryId}")
    Call<PlaceResponse> getPlaceCategory(@Path("categoryId") int id);

    @GET("places/viewByCategoryAndLocation/{categoryId}/{locationId}")
    Call<PlaceResponse> getPlaceCategoryAndLocation(@Path("categoryId") int categoryId, @Path("locationId") int locationId);

    @GET("places/searchInCategory")
    Call<PlaceResponse> getSearchInCategory(@Query("categoryId") int categoryId, @Query("query") String query);

    @GET("places/viewById/{id}")
    Call<PlaceResponse> getPlaceById(@Path("id") int id);

    @GET("comments/viewByPlace/{placeId}")
    Call<CommentResponse> getCommentByPlaceId(@Path("placeId") int placeId);

    @POST("users")
    Call<UserResponse>signup(@Body RequestBody object);
}
