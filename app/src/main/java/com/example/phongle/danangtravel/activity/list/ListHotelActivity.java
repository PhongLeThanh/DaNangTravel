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
import com.example.phongle.danangtravel.models.Hotel;
import com.example.phongle.danangtravel.models.Location;

import java.util.ArrayList;
import java.util.List;

public class ListHotelActivity extends AppCompatActivity implements ListHotelAdapter.onItemClickListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewListHotel;
    private Spinner mSpinnerDistrictList;
    private ListHotelAdapter mListHotelAdapter;
    private List<Hotel> mListHotel = new ArrayList<>();
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
        mRecyclerViewListHotel = findViewById(R.id.recyclerViewListPlace);
    }

    private void initAdapter() {
        // Set Adapter for spinner
        mDistrictSpinnerAdapter = new DistrictSpinnerAdapter(this, mListLocation);
        mSpinnerDistrictList.setAdapter(mDistrictSpinnerAdapter);
        //Set Adapter for recycler view
        mListHotelAdapter = new ListHotelAdapter(mListHotel, this);
        mRecyclerViewListHotel.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewListHotel.setAdapter(mListHotelAdapter);
    }

    private void initData() {
        // Init Data for recycler view List Attraction
//        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 200000));
//        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 200000));
//        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 200000));
//        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 200000));
//        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 200000));
//        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 200000));
    }

    @SuppressLint("ShowToast")
    @Override
    public void onPlaceClick(int position) {
        Toast.makeText(this, "Click", Toast.LENGTH_LONG);
    }
}
