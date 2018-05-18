package com.example.phongle.danangtravel.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TravelService {
    @GET("locations")
    Call<LocationResponse> getLocations();

    @GET("categories")
    Call<CategoryResponse> getCategory();

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

    @GET("places/searchAround")
    Call<PlaceAroundResponse> getSearchAround(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("places/searchAroundByCategory")
    Call<PlaceAroundResponse> getSearchAroundByCategory(@Query("latitude") double latitude, @Query("longitude") double longitude, @Query("categoryId") int categoryId);

    @GET("places/viewById/{id}")
    Call<PlaceResponse> getPlaceById(@Path("id") int id);

    @POST("comments/")
    Call<ObjectCommentResponse> postComment(@Header("Authorization") String token, @Body RequestBody comment);

    @POST("comments/updateRatingPlace")
    Call<CommentResponse> updateRatingPlace(@Body RequestBody data);

    @GET("comments/viewByPlace/{placeId}")
    Call<CommentResponse> getCommentByPlaceId(@Path("placeId") int placeId);

    @GET("users/{id}")
    Call<UserResponse> getUser(@Path("id") int id);

    @POST("users")
    Call<UserResponse> signup(@Body RequestBody object);

    @PUT("users/{id}")
    Call<UpdatePasswordResponse> updatePass(@Path("id") int userId, @Body RequestBody object);

    @PUT("profiles/{id}")
    Call<UpdateProfileResponse> updateProfile(@Path("id") int userId, @Body RequestBody object);

    @GET("events")
    Call<EventResponse> getListEvent();

    @GET("events/viewHot")
    Call<EventResponse> getListHotEvent();

    @GET("events/viewById/{id}")
    Call<EventResponse> getEventById(@Path("id") int id);

    @POST("likeevents")
    Call<LikeEventResponse> like(@Header("Authorization") String token, @Body RequestBody like);

    @HTTP(method = "DELETE", path = "likeevents", hasBody = true)
    Call<LikeEventResponse> disLike(@Header("Authorization") String token, @Body RequestBody dislike);
}
