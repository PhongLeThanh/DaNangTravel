package com.example.phongle.danangtravel.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.utils.ReWriteUrl;
import com.example.phongle.danangtravel.api.EventResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.models.Event;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EVENT_ID_KEY = "eventId";
    private Toolbar mToolbar;
    private ImageView mImgBack;
    private ImageView mImgLike;
    private ImageView mImgEvent;
    private TextView mTvEventName;
    private TextView mTvTimeStart;
    private TextView mTvTimeFinish;
    private TextView mTvAddress;
    private TextView mTvDetail;
    private TextView mTvNumLike;
    private Button mBtnSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        initViews();
        initListener();
        updateViews();
        setSupportActionBar(mToolbar);
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbarDetailEvent);
        mImgBack = findViewById(R.id.imgBack);
        mImgLike = findViewById(R.id.imgLike);
        mImgEvent = findViewById(R.id.imgDetailEvent);
        mTvEventName = findViewById(R.id.tvEventName);
        mTvTimeStart = findViewById(R.id.tvTimeStart);
        mTvTimeFinish = findViewById(R.id.tvTimeFinish);
        mTvAddress = findViewById(R.id.tvAddressEvent);
        mTvDetail = findViewById(R.id.tvDetailEvent);
        mTvNumLike = findViewById(R.id.tvNumLike);
        mBtnSearch = findViewById(R.id.btnSearchNearEvent);
    }

    private void initListener() {
        mImgBack.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
    }

    private void updateViews() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(EVENT_ID_KEY, 0);
        MyRetrofit.getInstance().getService().getEventById(id).enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse eventResponse = response.body();
                if (eventResponse != null && !eventResponse.getData().isEmpty()) {
                    Event event = eventResponse.getData().get(0);
                    if (event.getImage() != null) {
                        Glide.with(mImgEvent.getContext()).load(ReWriteUrl.reWriteUrl(event.getImage()))
                                .into(mImgEvent);
                    }
                    mTvEventName.setText(event.getEventName());
                    mTvTimeStart.setText(event.getStart().substring(0, 10));
                    mTvTimeFinish.setText(event.getFinish().substring(0, 10));
                    mTvAddress.setText(event.getAddress());
                    mTvDetail.setText(event.getDetail());
                    mTvNumLike.setText(event.getNumlike());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: failed");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.imgLike:

                break;
            case R.id.btnSearchNearEvent:
                break;
        }
    }
}
