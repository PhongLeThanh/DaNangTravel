package com.example.phongle.danangtravel;

import android.app.Application;

import com.example.phongle.danangtravel.api.MyRetrofit;

public class DanangTravelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyRetrofit.getInstance().initRetrofit();
    }
}
