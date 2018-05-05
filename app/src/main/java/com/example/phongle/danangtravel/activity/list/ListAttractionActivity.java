package com.example.phongle.danangtravel.activity.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Spinner;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.detail.DetailPlaceActivity;
import com.example.phongle.danangtravel.activity.headerHome.DistrictSpinnerAdapter;
import com.example.phongle.danangtravel.models.Location;
import com.example.phongle.danangtravel.models.TouristAttraction;

import java.util.ArrayList;
import java.util.List;

public class ListAttractionActivity extends AppCompatActivity implements ListAttractionAdapter.onItemClickListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewListAttraction;
    private Spinner mSpinnerDistrictList;
    private ListAttractionAdapter mListAttractionAdapter;
    private List<TouristAttraction> mListTourist = new ArrayList<>();
    private DistrictSpinnerAdapter mDistrictSpinnerAdapter;
    private List<Location> mListLocation = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        initView();
        initAdapter();

//        MyRetrofit.getInstance().getService().getPlace().enqueue(new Callback<PlaceResponse>() {
//            @Override
//            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
//                Log.d("xxx", "onResponse: " + response.isSuccessful());
//                PlaceResponse pr = response.body();
//                Log.d("xxx", "onResponse: " + pr.getData().get(0).getAddress());
//            }
//
//            @Override
//            public void onFailure(Call<PlaceResponse> call, Throwable t) {
//                Log.d("xxx", "onFailure: " + t.getMessage());
//            }
//        });
//        MyRetrofit.getInstance().getService().getPlaceCategory(3).enqueue(new Callback<PlaceResponse>() {
//            @Override
//            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
//                    PlaceResponse pr = response.body();
//                    //mListTourist = pr.getData();
//                //Log.d("xxx", "onResponse: "+mListTourist.get(0).getPlaceName().toString());
////                Log.d("xxx", "onResponse 222222: " + pr.getData().get(0).getAddress());
//            }
//
//            @Override
//            public void onFailure(Call<PlaceResponse> call, Throwable t) {
//                Log.d("xxx", "onFailure: " + t.getMessage());
//            }
//        });


        setSupportActionBar(mToolbar);

    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbarList);
        mRecyclerViewListAttraction = findViewById(R.id.recyclerViewListPlace);
        mSpinnerDistrictList = findViewById(R.id.spinnerDistrictList);
    }

    private void initAdapter() {
        // Set Adapter for spinner
        mDistrictSpinnerAdapter = new DistrictSpinnerAdapter(this, mListLocation);
        mSpinnerDistrictList.setAdapter(mDistrictSpinnerAdapter);
        //Set Adapter for recycler view
        Log.d("xxx", "initAdapter: " + mListTourist.size());
        mListAttractionAdapter = new ListAttractionAdapter(mListTourist, this);
        mRecyclerViewListAttraction.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewListAttraction.setAdapter(mListAttractionAdapter);
    }

    private void initData() {
        // Init Data for recycler view List Attraction
//        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
//        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
//        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
//        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
//        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
    }

    @Override
    public void onPlaceClick(int position) {
        Intent intent = new Intent(ListAttractionActivity.this, DetailPlaceActivity.class);
        startActivity(intent);
    }
}
