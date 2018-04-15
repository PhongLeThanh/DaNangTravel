package com.example.phongle.danangtravel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.bodyHome.TopHotelAdapter;
import com.example.phongle.danangtravel.activity.bodyHome.TopPlaceAdapter;
import com.example.phongle.danangtravel.activity.bodyHome.TopRestaurantAdapter;
import com.example.phongle.danangtravel.activity.headerHome.DistrictSpinnerAdapter;
import com.example.phongle.danangtravel.activity.headerHome.HeaderAdapter;
import com.example.phongle.danangtravel.activity.list.ListAttractionActivity;
import com.example.phongle.danangtravel.activity.list.ListHotelActivity;
import com.example.phongle.danangtravel.activity.list.ListRestaurantActivity;
import com.example.phongle.danangtravel.models.Hotel;
import com.example.phongle.danangtravel.models.Place;
import com.example.phongle.danangtravel.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        TopPlaceAdapter.onItemClickListener,
        TopRestaurantAdapter.onItemClickListener,
        TopHotelAdapter.onItemClickListener {

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private Spinner mSpinnerDistrict;
    private RecyclerView mRecyclerViewTopPlace;
    private RecyclerView mRecyclerViewTopRestaurant;
    private RecyclerView mRecyclerViewTopHotel;
    private Button mBtnListAttraction;
    private Button mBtnListRestaurant;
    private Button mBtnListHotel;
    private HeaderAdapter mHeaderAdapter;
    private TopPlaceAdapter mTopPlaceAdapter;
    private TopRestaurantAdapter mTopRestaurantAdapter;
    private TopHotelAdapter mTopHotelAdapter;
    private int mListImage[] = {R.drawable.bg_banner_app, R.drawable.bg_banner_app_2, R.drawable.bg_banner_app_3};
    private String[] mListDistrict = {"Hai Chau", "Hoa Vang", "Lien Chieu", "Thanh Khe", "Son Tra", "Cam Le"};
    private List<Place> mListPlace = new ArrayList<>();
    private List<Restaurant> mListRestaurant = new ArrayList<>();
    private List<Hotel> mListHotel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initListener();
        initAdapter();
        initCircleIndicator();
        initData();
        setSupportActionBar(mToolbar);
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPagerHeaderHome);
        mToolbar = findViewById(R.id.toolbarHome);
        mSpinnerDistrict = findViewById(R.id.spinnerDistrict);
        mRecyclerViewTopPlace = findViewById(R.id.recyclerViewTopPlace);
        mRecyclerViewTopRestaurant = findViewById(R.id.recyclerViewTopRestaurant);
        mRecyclerViewTopHotel = findViewById(R.id.recyclerViewTopHotel);
        mBtnListAttraction = findViewById(R.id.btnTouristAttraction);
        mBtnListRestaurant = findViewById(R.id.btnRestaurant);
        mBtnListHotel = findViewById(R.id.btnHotel);
    }

    private void initListener() {
        mBtnListAttraction.setOnClickListener(this);
        mBtnListRestaurant.setOnClickListener(this);
        mBtnListHotel.setOnClickListener(this);
    }

    private void initAdapter() {
        mHeaderAdapter = new HeaderAdapter(this, mListImage);
        mViewPager.setAdapter(mHeaderAdapter);
        mSpinnerDistrict.setAdapter(new DistrictSpinnerAdapter(this, mListDistrict));
        // Set Adapter for recycler view top place
        mTopPlaceAdapter = new TopPlaceAdapter(mListPlace, this);
        mRecyclerViewTopPlace.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewTopPlace.setAdapter(mTopPlaceAdapter);
        // Set Adapter for recycler view top restaurant
        mTopRestaurantAdapter = new TopRestaurantAdapter(mListRestaurant, this);
        mRecyclerViewTopRestaurant.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewTopRestaurant.setAdapter(mTopRestaurantAdapter);
        //Set Adapter for recycler view top hotel
        mTopHotelAdapter = new TopHotelAdapter(mListHotel, this);
        mRecyclerViewTopHotel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewTopHotel.setAdapter(mTopHotelAdapter);

    }

    private void initCircleIndicator() {
        CircleIndicator circleIndicator = findViewById(R.id.circleIndicator);
        circleIndicator.setViewPager(mViewPager);
    }

    private void initData() {
        // Init Data for recycler view Top Place
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
        mListPlace.add(new Place("Cầu Rồng", R.drawable.bg_place, 5, 3));
        // Init Data for recycler view Top Restaurant
        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
        mListRestaurant.add(new Restaurant("Ẩm thực Trần", R.drawable.bg_restaurant, 5, 3, 200000));
        // Init Data for recycler view Top Hotel
        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 3000000));
        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 3000000));
        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 3000000));
        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 3000000));
        mListHotel.add(new Hotel("Resort Intercontinental", R.drawable.bg_hotel, 5, 3, 3000000));
    }

    @SuppressLint("ShowToast")
    @Override
    public void onPlaceClick(int position) {
        Toast.makeText(this, "Click", Toast.LENGTH_LONG);
    }

    @SuppressLint("ShowToast")
    @Override
    public void onRestaurantClick(int position) {
        Toast.makeText(this, "Clicked restaurant", Toast.LENGTH_LONG);
    }

    @SuppressLint("ShowToast")
    @Override
    public void onHotelClick(int postion) {
        Toast.makeText(this, "Clicked restaurant", Toast.LENGTH_LONG);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnTouristAttraction:
                intent = new Intent(HomeActivity.this, ListAttractionActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRestaurant:
                intent = new Intent(HomeActivity.this, ListRestaurantActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHotel:
                intent = new Intent(HomeActivity.this, ListHotelActivity.class);
                startActivity(intent);
                break;
        }
    }
}