package com.example.phongle.danangtravel.activity.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.detail.DetailPlaceActivity;
import com.example.phongle.danangtravel.activity.headerHome.LocationAdapter;
import com.example.phongle.danangtravel.activity.headerHome.LocationDialog;
import com.example.phongle.danangtravel.api.LocationResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.PlaceResponse;
import com.example.phongle.danangtravel.models.Location;
import com.example.phongle.danangtravel.models.Place;
import com.example.phongle.danangtravel.models.TouristAttraction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAttractionActivity extends AppCompatActivity implements
        View.OnClickListener,
        ListAttractionAdapter.onItemClickListener,
        LocationAdapter.OnLocationClickListener {
    private static final String PLACE_ID_KEY = "id";
    private Toolbar mToolbar;
    private ImageView mBtnBack;
    private TextView mTvLocation;
    private LocationDialog mLocationDialog;
    private RecyclerView mRecyclerViewListAttraction;
    private ListAttractionAdapter mListAttractionAdapter;
    private List<TouristAttraction> mListTourist = new ArrayList<>();
    private List<Location> mListLocation = new ArrayList<>();
    private TextView mTvNotFound;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        initView();
        initListener();
        initAdapter();
        initData();
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.actionSearch);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    MyRetrofit.getInstance().getService().getSearchInCategory(3, query).enqueue(new Callback<PlaceResponse>() {
                        @Override
                        public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                            PlaceResponse placeResponse = response.body();
                            mListTourist.clear();
                            if (placeResponse.getData() != null && !placeResponse.getData().isEmpty()) {
                                List<Place> placeList = placeResponse.getData();
                                for (Place place : placeList) {
                                    TouristAttraction touristAttraction;
                                    if (place.getTouristattraction() != null) {
                                        touristAttraction = place.getTouristattraction();
                                    } else {
                                        touristAttraction = new TouristAttraction(0, "");
                                    }
                                    touristAttraction.setPlace(place);
                                    mListTourist.add(touristAttraction);
                                }
                                mTvNotFound.setVisibility(View.GONE);
                            } else {
                                mTvNotFound.setVisibility(View.VISIBLE);
                            }
                            mListAttractionAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<PlaceResponse> call, Throwable t) {

                        }
                    });
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;

    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbarList);
        mBtnBack = findViewById(R.id.imgBackToHome);
        mTvLocation = findViewById(R.id.tvLocation);
        mLocationDialog = new LocationDialog();
        mLocationDialog.setCallback(this);
        mRecyclerViewListAttraction = findViewById(R.id.recyclerViewListPlace);
        mTvNotFound = findViewById(R.id.tvNotFound);
    }

    private void initListener() {
        mBtnBack.setOnClickListener(this);
        mTvLocation.setOnClickListener(this);
    }

    private void initAdapter() {
        //Set Adapter for recycler view
        mListAttractionAdapter = new ListAttractionAdapter(mListTourist, this);
        mRecyclerViewListAttraction.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewListAttraction.setAdapter(mListAttractionAdapter);
    }

    private void initData() {
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
                Log.d("xxx", "onFailure: " + t.getMessage());
            }
        });
        getListPlaceByAllLocation();
    }

    @Override
    public void onPlaceClick(int position) {
        Intent intent = new Intent(this, DetailPlaceActivity.class);
        intent.putExtra(PLACE_ID_KEY, mListTourist.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBackToHome:
                onBackPressed();
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
        if (location.getId() == 0) {
            getListPlaceByAllLocation();
        } else {
            MyRetrofit.getInstance().getService().getPlaceCategoryAndLocation(3, location.getId()).enqueue(new Callback<PlaceResponse>() {
                @Override
                public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                    PlaceResponse placeResponse = response.body();
                    mListTourist.clear();
                    if (!placeResponse.getData().isEmpty()) {
                        List<Place> placeList = placeResponse.getData();
                        for (Place place : placeList) {
                            TouristAttraction touristAttraction;
                            if (place.getTouristattraction() != null) {
                                touristAttraction = place.getTouristattraction();
                            } else {
                                touristAttraction = new TouristAttraction(0, "");
                            }
                            touristAttraction.setPlace(place);
                            mListTourist.add(touristAttraction);
                        }
                        mTvNotFound.setVisibility(View.GONE);
                    } else {
                        mTvNotFound.setVisibility(View.VISIBLE);
                    }
                    mListAttractionAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<PlaceResponse> call, Throwable t) {
                    Log.d("xxx", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private void getListPlaceByAllLocation() {
        MyRetrofit.getInstance().getService().getPlaceCategory(3).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                PlaceResponse placeResponse = response.body();
                mListTourist.clear();
                if (!placeResponse.getData().isEmpty()) {
                    List<Place> placeList = placeResponse.getData();
                    for (Place place : placeList) {
                        TouristAttraction touristAttraction;
                        if (place.getTouristattraction() != null) {
                            touristAttraction = place.getTouristattraction();
                        } else {
                            touristAttraction = new TouristAttraction(0, "");
                        }
                        touristAttraction.setPlace(place);
                        mListTourist.add(touristAttraction);
                    }
                    mTvNotFound.setVisibility(View.GONE);
                } else {
                    mTvNotFound.setVisibility(View.VISIBLE);
                }
                mListAttractionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: " + t.getMessage());
            }
        });

    }
}
