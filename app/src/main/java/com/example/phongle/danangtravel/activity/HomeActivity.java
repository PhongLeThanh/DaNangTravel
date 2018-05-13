package com.example.phongle.danangtravel.activity;

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
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.aroundhere.ListPlaceAroundActivity;
import com.example.phongle.danangtravel.activity.bodyHome.TopHotelAdapter;
import com.example.phongle.danangtravel.activity.bodyHome.TopRestaurantAdapter;
import com.example.phongle.danangtravel.activity.bodyHome.TopTouristAdapter;
import com.example.phongle.danangtravel.activity.detail.DetailPlaceActivity;
import com.example.phongle.danangtravel.activity.headerHome.HeaderAdapter;
import com.example.phongle.danangtravel.activity.headerHome.LocationAdapter;
import com.example.phongle.danangtravel.activity.headerHome.LocationDialog;
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
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        TopTouristAdapter.onItemClickListener,
        TopRestaurantAdapter.onItemClickListener,
        TopHotelAdapter.onItemClickListener,
        LocationAdapter.OnLocationClickListener {
    private static final String PLACE_ID_KEY = "id";

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TextView mTvLocation;
    private AVLoadingIndicatorView mLoadingViewTopPlace;
    private AVLoadingIndicatorView mLoadingViewTopHotel;
    private AVLoadingIndicatorView mLoadingViewTopRestaurant;
    private RecyclerView mRecyclerViewTopPlace;
    private RecyclerView mRecyclerViewTopRestaurant;
    private RecyclerView mRecyclerViewTopHotel;
    private Button mBtnListAttraction;
    private Button mBtnListRestaurant;
    private Button mBtnListHotel;
    private Button mBtnListNearPlace;
    private TextView mTvMoreHotel;
    private TextView mTvMoreRestaurant;
    private TextView mTvMorePlace;
    private HeaderAdapter mHeaderAdapter;
    private TopTouristAdapter mTopTouristAdapter;
    private TopRestaurantAdapter mTopRestaurantAdapter;
    private TopHotelAdapter mTopHotelAdapter;
    private int mListImage[] = {R.drawable.bg_banner_app, R.drawable.bg_banner_app_2, R.drawable.bg_banner_app_3};
    private List<Location> mListLocation = new ArrayList<>();
    private List<Restaurant> mListRestaurant = new ArrayList<>();
    private List<Hotel> mListHotel = new ArrayList<>();
    private List<TouristAttraction> mListTourist = new ArrayList<>();
    private LocationDialog mLocationDialog;

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
        mTvLocation = findViewById(R.id.tvLocation);
        mLoadingViewTopPlace = findViewById(R.id.loadingViewTopPlace);
        mLoadingViewTopHotel = findViewById(R.id.loadingViewTopHotel);
        mLoadingViewTopRestaurant = findViewById(R.id.loadingViewTopRestaurant);
        mRecyclerViewTopPlace = findViewById(R.id.recyclerViewTopPlace);
        mRecyclerViewTopRestaurant = findViewById(R.id.recyclerViewTopRestaurant);
        mRecyclerViewTopHotel = findViewById(R.id.recyclerViewTopHotel);
        mTvMorePlace = findViewById(R.id.tvMoreTopPlace);
        mTvMoreRestaurant = findViewById(R.id.tvMoreTopRestaurant);
        mTvMoreHotel = findViewById(R.id.tvMoreTopHotel);
        mBtnListAttraction = findViewById(R.id.btnTouristAttraction);
        mBtnListRestaurant = findViewById(R.id.btnRestaurant);
        mBtnListHotel = findViewById(R.id.btnHotel);
        mBtnListNearPlace = findViewById(R.id.btnNearPlaces);
        mLocationDialog = new LocationDialog();
        mLocationDialog.setCallback(this);
    }

    private void initListener() {
        mBtnListAttraction.setOnClickListener(this);
        mBtnListRestaurant.setOnClickListener(this);
        mBtnListHotel.setOnClickListener(this);
        mBtnListNearPlace.setOnClickListener(this);
        mTvLocation.setOnClickListener(this);
        mTvMorePlace.setOnClickListener(this);
        mTvMoreRestaurant.setOnClickListener(this);
        mTvMoreHotel.setOnClickListener(this);
    }

    private void initAdapter() {
        mHeaderAdapter = new HeaderAdapter(this, mListImage);
        mViewPager.setAdapter(mHeaderAdapter);
        // Set Adapter for recycler view top place
        mTopTouristAdapter = new TopTouristAdapter(this, mListTourist, this);
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

    private void showLoadingView() {
        mLoadingViewTopPlace.show();
        mLoadingViewTopHotel.show();
        mLoadingViewTopRestaurant.show();
    }

    ;

    private void hideLoadingView() {
        mLoadingViewTopPlace.hide();
        mLoadingViewTopHotel.hide();
        mLoadingViewTopRestaurant.hide();
    }

    private void initData() {
        showLoadingView();
        MyRetrofit.getInstance().getService().getLocations().enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                LocationResponse locationResponse = response.body();
                mListLocation.add(new Location(0, 0, "Tất cả", null, null, null, null, null));
                if (!locationResponse.getData().isEmpty()) {
                    for (Location location : locationResponse.getData()) {
                        mListLocation.add(location);
                    }
                    mLocationDialog.setData(mListLocation);
                    hideLoadingView();
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
            }
        });
        getListTopPlace();
    }

    @Override
    public void onPlaceClick(int position) {
        Intent intent = new Intent(this, DetailPlaceActivity.class);
        intent.putExtra(PLACE_ID_KEY, mListTourist.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onRestaurantClick(int position) {
        Intent intent = new Intent(this, DetailPlaceActivity.class);
        intent.putExtra(PLACE_ID_KEY, mListRestaurant.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onHotelClick(int position) {
        Intent intent = new Intent(this, DetailPlaceActivity.class);
        intent.putExtra(PLACE_ID_KEY, mListHotel.get(position).getId());
        startActivity(intent);
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
            case R.id.btnNearPlaces:
                intent = new Intent(HomeActivity.this, ListPlaceAroundActivity.class);
                startActivity(intent);
                break;
            case R.id.tvMoreTopPlace:
                intent = new Intent(HomeActivity.this, ListAttractionActivity.class);
                startActivity(intent);
                break;
            case R.id.tvMoreTopRestaurant:
                intent = new Intent(HomeActivity.this, ListRestaurantActivity.class);
                startActivity(intent);
                break;
            case R.id.tvMoreTopHotel:
                intent = new Intent(HomeActivity.this, ListHotelActivity.class);
                startActivity(intent);
                break;
            case R.id.tvLocation:
                mLocationDialog.show(getFragmentManager(), LocationDialog.class.getSimpleName());
                break;
        }
    }

    @Override
    public void onLocationClick(Location location) {
        mLocationDialog.dismiss();
        mTvLocation.setText(location.getLocationName());
        showLoadingView();
        if (location.getId() == 0) {
            getListTopPlace();
        } else {
            MyRetrofit.getInstance().getService().getTopPlaceInLocation(location.getId()).enqueue(new Callback<PlaceResponse>() {
                @Override
                public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                    PlaceResponse placeResponse = response.body();
                    mListRestaurant.clear();
                    mListHotel.clear();
                    mListTourist.clear();
                    if (!placeResponse.getData().isEmpty()) {
                        List<Place> placeList = placeResponse.getData();
                        for (Place place : placeList) {
                            if (place.getCategoryId() == 1) {
                                Restaurant restaurant;
                                if (place.getRestaurant() != null) {
                                    restaurant = place.getRestaurant();
                                } else {
                                    restaurant = new Restaurant("", "", "");
                                }
                                restaurant.setPlace(place);
                                mListRestaurant.add(restaurant);
                            }
                            if (place.getCategoryId() == 2) {
                                Hotel hotel;
                                if (place.getHotel() != null) {
                                    hotel = place.getHotel();
                                } else {
                                    hotel = new Hotel(0, "", "");
                                }
                                hotel.setPlace(place);
                                mListHotel.add(hotel);
                            }
                            if (place.getCategoryId() == 3) {
                                TouristAttraction touristAttraction;
                                if (place.getTouristattraction() != null) {
                                    touristAttraction = place.getTouristattraction();
                                } else {
                                    touristAttraction = new TouristAttraction(0, "");
                                }
                                touristAttraction.setPlace(place);
                                mListTourist.add(touristAttraction);
                            }
                        }
                        mTopRestaurantAdapter.notifyDataSetChanged();
                        mTopHotelAdapter.notifyDataSetChanged();
                        mTopTouristAdapter.notifyDataSetChanged();
                    }
                    hideLoadingView();

                }

                @Override
                public void onFailure(@NonNull Call<PlaceResponse> call, Throwable t) {
                    Log.d("xxx", "onResponse: fail ");
                }
            });

        }
    }

    private void getListTopPlace() {
        MyRetrofit.getInstance().getService().getTopPlace().enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, Response<PlaceResponse> response) {
                PlaceResponse placeResponse = response.body();
                mListRestaurant.clear();
                mListHotel.clear();
                mListTourist.clear();
                if (!placeResponse.getData().isEmpty()) {
                    List<Place> placeList = placeResponse.getData();
                    for (Place place : placeList) {
                        if (place.getCategoryId() == 1) {
                            Restaurant restaurant;
                            if (place.getRestaurant() != null) {
                                restaurant = place.getRestaurant();
                            } else {
                                restaurant = new Restaurant("", "", "");
                            }
                            restaurant.setPlace(place);
                            mListRestaurant.add(restaurant);
                        }
                        if (place.getCategoryId() == 2) {
                            Hotel hotel;
                            if (place.getHotel() != null) {
                                hotel = place.getHotel();
                            } else {
                                hotel = new Hotel(0, "", "");
                            }
                            hotel.setPlace(place);
                            mListHotel.add(hotel);
                        }
                        if (place.getCategoryId() == 3) {
                            TouristAttraction touristAttraction;
                            if (place.getTouristattraction() != null) {
                                touristAttraction = place.getTouristattraction();
                            } else {
                                touristAttraction = new TouristAttraction(0, "");
                            }
                            touristAttraction.setPlace(place);
                            mListTourist.add(touristAttraction);
                        }
                    }
                    mTopRestaurantAdapter.notifyDataSetChanged();
                    mTopHotelAdapter.notifyDataSetChanged();
                    mTopTouristAdapter.notifyDataSetChanged();
                }
                hideLoadingView();
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Log.d("xxx", "onResponse: fail ");
            }
        });
    }
}