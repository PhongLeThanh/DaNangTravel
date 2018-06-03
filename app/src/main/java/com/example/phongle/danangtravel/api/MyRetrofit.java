package com.example.phongle.danangtravel.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    private static final String API_BASE_URL = "http://10.10.31.221:6969/api/";
    private static TravelService mTravelService;
    private static AuthService mAuthService;
    private static MyRetrofit mMyRetrofit;

    public static MyRetrofit getInstance() {
        if (mMyRetrofit == null) {
            mMyRetrofit = new MyRetrofit();
        }
        return mMyRetrofit;
    }

    public void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mTravelService = retrofit.create(TravelService.class);
        mAuthService = retrofit.create(AuthService.class);
    }

    public TravelService getService() {
        return mTravelService;
    }

    public AuthService getAuthService() {
        return mAuthService;
    }
}
