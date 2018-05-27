package com.example.phongle.danangtravel.activity.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.detail.DetailEventActivity;
import com.example.phongle.danangtravel.activity.headerHome.HeaderAdapter;
import com.example.phongle.danangtravel.api.EventResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.models.Event;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListEventActivity extends AppCompatActivity implements ListEventAdapter.OnItemClickListener, View.OnClickListener {
    private static final String EVENT_ID_KEY = "eventId";
    private ViewPager mViewPagerHeader;
    private HeaderAdapter mHeaderAdapter;
    private ImageView mImgBack;
    private RecyclerView mRecyclerViewEvent;
    private ListEventAdapter mListEventAdapter;
    private int mListImage[] = {R.drawable.bg_event, R.drawable.bg_event2, R.drawable.bg_event3};
    private List<Event> mListEvent = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);
        initViews();
        initListener();
        initAdapter();
        initCircleIndicator();
        initData();

    }

    private void initViews() {
        mViewPagerHeader = findViewById(R.id.viewPagerHeaderEvent);
        mImgBack = findViewById(R.id.imgBack);
        mRecyclerViewEvent = findViewById(R.id.recyclerViewListEvent);
    }

    private void initListener() {
        mImgBack.setOnClickListener(this);
    }

    private void initCircleIndicator() {
        CircleIndicator circleIndicator = findViewById(R.id.circleIndicator);
        circleIndicator.setViewPager(mViewPagerHeader);
    }

    private void initAdapter() {
        mHeaderAdapter = new HeaderAdapter(this, mListImage);
        mViewPagerHeader.setAdapter(mHeaderAdapter);
        // Set adapter for recycler view List event
        mListEventAdapter = new ListEventAdapter(mListEvent, this, this);
        mRecyclerViewEvent.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewEvent.setAdapter(mListEventAdapter);
    }

    private void initData() {
        MyRetrofit.getInstance().getService().getListEvent().enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                EventResponse eventResponse = response.body();
                mListEvent.clear();
                if (eventResponse != null && !eventResponse.getData().isEmpty()) {
                    mListEvent.addAll(eventResponse.getData());
                }
                mListEventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                Log.d("xxx", "onFailure: failed"+t.getMessage());
            }
        });
    }

    @Override
    public void onEventClick(int position) {
        Intent intent = new Intent(this, DetailEventActivity.class);
        intent.putExtra(EVENT_ID_KEY, mListEvent.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
        }
    }
}
