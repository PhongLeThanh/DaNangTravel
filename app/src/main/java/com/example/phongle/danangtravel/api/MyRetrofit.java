package com.example.phongle.danangtravel.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    private static final String API_BASE_URL = "http://192.168.1.143:6969/api/";
    private static TravelService mTravelService;
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
                .build();
        mTravelService = retrofit.create(TravelService.class);
    }

    public TravelService getService() {
        return mTravelService;
    }
}
