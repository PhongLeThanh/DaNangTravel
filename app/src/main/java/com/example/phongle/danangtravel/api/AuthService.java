package com.example.phongle.danangtravel.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Observable<LoginResponse> login(@Body RequestBody requestBody);
}
