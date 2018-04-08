package com.example.phongle.danangtravel.activity.list;

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
import com.example.phongle.danangtravel.models.Place;

import java.util.ArrayList;
import java.util.List;

public class ListAttractionActivity extends AppCompatActivity implements ListAttractionAdapter.onItemClickListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewListAttraction;
    private Spinner mSpinnerDistrictList;
    private ListAttractionAdapter mListAttractionAdapter;
    private DistrictSpinnerAdapter mDistrictSpinnerAdapter;
    private List<Place> mListPlace = new ArrayList<>();
    private String[] mListDistrict = {"Hai Chau", "Hoa Vang", "Lien Chieu", "Thanh Khe", "Son Tra", "Cam Le"};

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
        mRecyclerViewListAttraction = findViewById(R.id.recyclerViewListPlace);
        mSpinnerDistrictList = findViewById(R.id.spinnerDistrictList);
    }

    private void initAdapter() {
        // Set Adapter for spinner
        mDistrictSpinnerAdapter = new DistrictSpinnerAdapter(this, mListDistrict);
        mSpinnerDistrictList.setAdapter(mDistrictSpinnerAdapter);
        //Set Adapter for recycler view
        mListAttractionAdapter = new ListAttractionAdapter(mListPlace, this);
        mRecyclerViewListAttraction.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewListAttraction.setAdapter(mListAttractionAdapter);
    }

    private void initData() {
        // Init Data for recycler view List Attraction
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
    }

    @Override
    public void onPlaceClick(int postion) {
        Toast.makeText(this, "Click", Toast.LENGTH_LONG);
    }
}
