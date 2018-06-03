package com.example.phongle.danangtravel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.phongle.danangtravel.activity.bodyHome.HotEventAdapter;
import com.example.phongle.danangtravel.activity.bodyHome.TopHotelAdapter;
import com.example.phongle.danangtravel.activity.bodyHome.TopRestaurantAdapter;
import com.example.phongle.danangtravel.activity.bodyHome.TopTouristAdapter;
import com.example.phongle.danangtravel.activity.detail.DetailEventActivity;
import com.example.phongle.danangtravel.activity.detail.DetailPlaceActivity;
import com.example.phongle.danangtravel.activity.event.ListEventActivity;
import com.example.phongle.danangtravel.activity.headerHome.HeaderAdapter;
import com.example.phongle.danangtravel.activity.headerHome.LocationAdapter;
import com.example.phongle.danangtravel.activity.headerHome.LocationDialog;
import com.example.phongle.danangtravel.activity.list.ListAttractionActivity;
import com.example.phongle.danangtravel.activity.list.ListHotelActivity;
import com.example.phongle.danangtravel.activity.list.ListRestaurantActivity;
import com.example.phongle.danangtravel.activity.login.LoginActivity;
import com.example.phongle.danangtravel.activity.profile.ProfileActivity;
import com.example.phongle.danangtravel.api.EventResponse;
import com.example.phongle.danangtravel.api.LocationResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.PlaceResponse;
import com.example.phongle.danangtravel.models.Event;
import com.example.phongle.danangtravel.models.Hotel;
import com.example.phongle.danangtravel.models.Location;
import com.example.phongle.danangtravel.models.Place;
import com.example.phongle.danangtravel.models.Restaurant;
import com.example.phongle.danangtravel.models.TouristAttraction;
import com.example.phongle.danangtravel.models.shareds.SharedPrefeencesUtils;
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
        LocationAdapter.OnLocationClickListener, HotEventAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String PLACE_ID_KEY = "id";
    private static final String EVENT_ID_KEY = "eventId";
    private static final int DETAIL_REQUEST = 100;

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TextView mTvLocation;
    private SwipeRefreshLayout mRefreshHome;
    private AVLoadingIndicatorView mLoadingViewTopPlace;
    private AVLoadingIndicatorView mLoadingViewTopHotel;
    private AVLoadingIndicatorView mLoadingViewTopRestaurant;
    private AVLoadingIndicatorView mLoadingViewHotEvent;
    private RecyclerView mRecyclerViewHotEvent;
    private RecyclerView mRecyclerViewTopPlace;
    private RecyclerView mRecyclerViewTopRestaurant;
    private RecyclerView mRecyclerViewTopHotel;
    private Button mBtnListAttraction;
    private Button mBtnListRestaurant;
    private Button mBtnListHotel;
    private Button mBtnListNearPlace;
    private Button mBtnYou;
    private TextView mTvMoreEvent;
    private TextView mTvMoreHotel;
    private TextView mTvMoreRestaurant;
    private TextView mTvMorePlace;
    private HeaderAdapter mHeaderAdapter;
    private HotEventAdapter mHotEventAdapter;
    private TopTouristAdapter mTopTouristAdapter;
    private TopRestaurantAdapter mTopRestaurantAdapter;
    private TopHotelAdapter mTopHotelAdapter;
    private int mListImage[] = {R.drawable.bg_banner_app, R.drawable.bg_banner_app_2, R.drawable.bg_banner_app_3};
    private LocationDialog mLocationDialog;
    private List<Location> mListLocation = new ArrayList<>();
    private List<Event> mListEvent = new ArrayList<>();
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
        onRefresh();
        initCircleIndicator();
        setSupportActionBar(mToolbar);
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPagerHeaderHome);
        mToolbar = findViewById(R.id.toolbarHome);
        mTvLocation = findViewById(R.id.tvLocation);
        mRefreshHome = findViewById(R.id.refreshHome);
        mLoadingViewTopPlace = findViewById(R.id.loadingViewTopPlace);
        mLoadingViewTopHotel = findViewById(R.id.loadingViewTopHotel);
        mLoadingViewTopRestaurant = findViewById(R.id.loadingViewTopRestaurant);
        mLoadingViewHotEvent = findViewById(R.id.loadingViewEvent);
        mRecyclerViewHotEvent = findViewById(R.id.recyclerViewListHotEvent);
        mRecyclerViewTopPlace = findViewById(R.id.recyclerViewTopPlace);
        mRecyclerViewTopRestaurant = findViewById(R.id.recyclerViewTopRestaurant);
        mRecyclerViewTopHotel = findViewById(R.id.recyclerViewTopHotel);
        mTvMoreEvent = findViewById(R.id.tvMoreEvent);
        mTvMorePlace = findViewById(R.id.tvMoreTopPlace);
        mTvMoreRestaurant = findViewById(R.id.tvMoreTopRestaurant);
        mTvMoreHotel = findViewById(R.id.tvMoreTopHotel);
        mBtnListAttraction = findViewById(R.id.btnTouristAttraction);
        mBtnListRestaurant = findViewById(R.id.btnRestaurant);
        mBtnListHotel = findViewById(R.id.btnHotel);
        mBtnListNearPlace = findViewById(R.id.btnNearPlaces);
        mBtnYou = findViewById(R.id.btnYou);
        mLocationDialog = new LocationDialog();
        mLocationDialog.setCallback(this);
    }

    private void initListener() {
        mRefreshHome.setOnRefreshListener(this);
        mBtnListAttraction.setOnClickListener(this);
        mBtnListRestaurant.setOnClickListener(this);
        mBtnListHotel.setOnClickListener(this);
        mBtnListNearPlace.setOnClickListener(this);
        mBtnYou.setOnClickListener(this);
        mTvLocation.setOnClickListener(this);
        mTvMoreEvent.setOnClickListener(this);
        mTvMorePlace.setOnClickListener(this);
        mTvMoreRestaurant.setOnClickListener(this);
        mTvMoreHotel.setOnClickListener(this);
    }

    private void initAdapter() {
        mHeaderAdapter = new HeaderAdapter(this, mListImage);
        mViewPager.setAdapter(mHeaderAdapter);
        // Set Adapter for recycler view hot event
        mHotEventAdapter = new HotEventAdapter(this, mListEvent, this);
        mRecyclerViewHotEvent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewHotEvent.setAdapter(mHotEventAdapter);
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
        mRefreshHome.setRefreshing(true);
        mLoadingViewTopPlace.show();
        mLoadingViewTopHotel.show();
        mLoadingViewTopRestaurant.show();
        mLoadingViewHotEvent.show();
    }

    private void hideLoadingView() {
        mRefreshHome.setRefreshing(false);
        mLoadingViewTopPlace.hide();
        mLoadingViewTopHotel.hide();
        mLoadingViewTopRestaurant.hide();
        mLoadingViewHotEvent.hide();
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
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
            }
        });
        getListHotEvent();
        getListTopPlace();
        hideLoadingView();
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
            case R.id.btnYou:
                if (SharedPrefeencesUtils.getDocument() == null) {
                    intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                } else {
                    intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    break;
                }
            case R.id.tvMoreEvent:
                intent = new Intent(HomeActivity.this, ListEventActivity.class);
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
                    }
                    mTopRestaurantAdapter.notifyDataSetChanged();
                    mTopHotelAdapter.notifyDataSetChanged();
                    mTopTouristAdapter.notifyDataSetChanged();
                    hideLoadingView();

                }

                @Override
                public void onFailure(@NonNull Call<PlaceResponse> call, Throwable t) {
                    Log.d("xxx", "onResponse: fail ");
                }
            });

        }
    }

    private void getListHotEvent() {
        MyRetrofit.getInstance().getService().getListHotEvent().enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse eventResponse = response.body();
                if (eventResponse != null && !eventResponse.getData().isEmpty()) {
                    mListEvent.clear();
                    mListEvent.addAll(eventResponse.getData());
                }
                mHotEventAdapter.notifyDataSetChanged();
                hideLoadingView();
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: failed");
            }
        });
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

    @Override
    public void onEventClick(int position) {
        Intent intent = new Intent(this, DetailEventActivity.class);
        intent.putExtra(EVENT_ID_KEY, mListEvent.get(position).getId());
        startActivityForResult(intent, DETAIL_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_REQUEST && resultCode == RESULT_OK) {
            getListHotEvent();
        }
    }

    @Override
    public void onRefresh() {
        getListHotEvent();
        getListTopPlace();
    }
}