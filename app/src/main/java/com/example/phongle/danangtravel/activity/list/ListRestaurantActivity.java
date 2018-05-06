package com.example.phongle.danangtravel.activity.list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.headerHome.DistrictSpinnerAdapter;
import com.example.phongle.danangtravel.models.Location;
import com.example.phongle.danangtravel.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class ListRestaurantActivity extends AppCompatActivity implements ListRestaurantAdapter.onItemClickListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewListRestaurant;
    private Spinner mSpinnerDistrictList;
    private ListRestaurantAdapter mListRestaurantAdapter;
    private List<Restaurant> mListRestaurant = new ArrayList<>();
    private DistrictSpinnerAdapter mDistrictSpinnerAdapter;
    private List<Location> mListLocation = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        initView();
        initAdapter();
        initData();
        setSupportActionBar(mToolbar);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbarList);
        mRecyclerViewListRestaurant = findViewById(R.id.recyclerViewListPlace);
    }
    private void initAdapter() {
        // Set Adapter for spinner
        mDistrictSpinnerAdapter = new DistrictSpinnerAdapter(this, mListLocation);
        mSpinnerDistrictList.setAdapter(mDistrictSpinnerAdapter);
        //Set Adapter for recycler view
        mListRestaurantAdapter = new ListRestaurantAdapter(mListRestaurant, this);
        mRecyclerViewListRestaurant.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewListRestaurant.setAdapter(mListRestaurantAdapter);
    }
    private void initData() {
        // Init Data for recycler view List Attraction
//        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
//        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
//        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
//        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
//        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
    }
    @SuppressLint("ShowToast")
    @Override
    public void onPlaceClick(int position) {
        Toast.makeText(this, "Click", Toast.LENGTH_LONG);
    }
}
