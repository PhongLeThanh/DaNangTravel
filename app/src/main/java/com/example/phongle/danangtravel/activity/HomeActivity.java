package com.example.phongle.danangtravel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.bodyHome.TopHotelAdapter;
import com.example.phongle.danangtravel.activity.bodyHome.TopRestaurantAdapter;
import com.example.phongle.danangtravel.activity.bodyHome.TopTouristAdapter;
import com.example.phongle.danangtravel.activity.headerHome.DistrictSpinnerAdapter;
import com.example.phongle.danangtravel.activity.headerHome.HeaderAdapter;
import com.example.phongle.danangtravel.activity.list.ListAttractionActivity;
import com.example.phongle.danangtravel.activity.list.ListHotelActivity;
import com.example.phongle.danangtravel.activity.list.ListRestaurantActivity;
import com.example.phongle.danangtravel.api.LocationResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.PlaceResponse;
import com.example.phongle.danangtravel.models.Hotel;
import com.example.phongle.danangtravel.models.Location;
import com.example.phongle.danangtravel.models.Place;
import com.example.phongle.danangtravel.models.Restaurant;
import com.example.phongle.danangtravel.models.TouristAttraction;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        TopTouristAdapter.onItemClickListener,
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
    private DistrictSpinnerAdapter mDistrictSpinnerAdapter;
    private TopTouristAdapter mTopTouristAdapter;
    private TopRestaurantAdapter mTopRestaurantAdapter;
    private TopHotelAdapter mTopHotelAdapter;
    private int mListImage[] = {R.drawable.bg_banner_app, R.drawable.bg_banner_app_2, R.drawable.bg_banner_app_3};
    private List<Location> mListLocation = new ArrayList<>();
    private List<Restaurant> mListRestaurant = new ArrayList<>();
    private List<Hotel> mListHotel = new ArrayList<>();
    private List<TouristAttraction> mListTourist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initListener();
        initAdapter();
        initData();
        initCircleIndicator();
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
        // Set Adapter for location spinner
//        mDistrictSpinnerAdapter = new DistrictSpinnerAdapter(this,mListLocation);
//        mSpinnerDistrict.setAdapter(mDistrictSpinnerAdapter);
        // Set Adapter for recycler view top place
        mTopTouristAdapter = new TopTouristAdapter(mListTourist, this);
        mRecyclerViewTopPlace.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewTopPlace.setAdapter(mTopTouristAdapter);
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
        MyRetrofit.getInstance().getService().getLocations().enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                LocationResponse locationResponse = response.body();
                for (Location location : locationResponse.getData()) {
                    mListLocation.add(location);
                }
                Log.d("xxx", "onResponse: "+mListLocation.get(0).getLocationName());
            }
            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
            }
        });
        mDistrictSpinnerAdapter = new DistrictSpinnerAdapter(this,mListLocation);
        mSpinnerDistrict.setAdapter(mDistrictSpinnerAdapter);
        MyRetrofit.getInstance().getService().getTopPlace().enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, Response<PlaceResponse> response) {
                PlaceResponse placeResponse = response.body();
                List<Place> placeList = placeResponse.getData();
                Log.d("xxx", "onResponse: " + placeList.get(0).getPlaceName());
                if (placeList != null) {
                    Log.d("xxx", "onResponse: " + placeList.toString());
                    for (Place place : placeList) {
                        if (place.getCategoryId() == 1) {
                            Restaurant restaurant=place.getRestaurant();
                            restaurant.setPlace(place);
                            mListRestaurant.add(restaurant);
                        }
                        if (place.getCategoryId() == 2 ) {
                            Hotel hotel = place.getHotel();
                            hotel.setPlace(place);
                            mListHotel.add(hotel);
                        }
                        if (place.getCategoryId() == 3) {
                           TouristAttraction touristAttraction = place.getTouristattraction();
                           touristAttraction.setPlace(place);
                           mListTourist.add(touristAttraction);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Log.d("xxx", "onResponse: fail ");
            }
        });
        mDistrictSpinnerAdapter.notifyDataSetChanged();
        mTopRestaurantAdapter.notifyDataSetChanged();
        mTopHotelAdapter.notifyDataSetChanged();
        mTopTouristAdapter.notifyDataSetChanged();
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