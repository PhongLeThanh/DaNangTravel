package com.example.phongle.danangtravel.activity.aroundhere;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.detail.CustomMakerMap;
import com.example.phongle.danangtravel.activity.detail.DetailPlaceActivity;
import com.example.phongle.danangtravel.api.CategoryResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.PlaceAroundResponse;
import com.example.phongle.danangtravel.models.Category;
import com.example.phongle.danangtravel.models.PlaceAround;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPlaceAroundActivity extends AppCompatActivity implements View.OnClickListener, CategoryAdapter.OnCategoryClickListener,
        ListPlaceAroundAdapter.OnItemClickListener,
        OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnInfoWindowClickListener {
    private static final String PLACE_ID_KEY = "id";
    private Toolbar mToolbar;
    private TextView mTvCategory;
    private ImageView mImgBackToHome;
    private RecyclerView mRecyclerViewListPlace;
    private ListPlaceAroundAdapter mListPlaceAroundAdapter;
    private List<PlaceAround> mListPlace = new ArrayList<>();
    private List<Category> mListCategory = new ArrayList<>();
    private CategoryDialog mCategoryDialog;
    private GoogleMap mGoogleMap;
    private android.location.Location mCurrentLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_around);
        initViews();
        initListeners();
        initAdapter();
        initData();
        setSupportActionBar(mToolbar);
        SupportMapFragment mapFragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAround);
        mapFragment.getMapAsync(this);
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbarAroundHere);
        mImgBackToHome = findViewById(R.id.imgBack);
        mTvCategory = findViewById(R.id.tvCategory);
        mRecyclerViewListPlace = findViewById(R.id.recyclerViewPlaceAround);
        mCategoryDialog = new CategoryDialog();
        mCategoryDialog.setCallback(this);
    }

    private void initListeners() {
        mImgBackToHome.setOnClickListener(this);
        mTvCategory.setOnClickListener(this);
    }

    private void initAdapter() {
        mListPlaceAroundAdapter = new ListPlaceAroundAdapter(mListPlace, this);
        mRecyclerViewListPlace.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewListPlace.setAdapter(mListPlaceAroundAdapter);
    }

    private void initData() {
        MyRetrofit.getInstance().getService().getCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                CategoryResponse categoryResponse = response.body();
                mListCategory.add(new Category(0, "Tất cả", null, null));
                if (!categoryResponse.getData().isEmpty()) {
                    for (Category category : categoryResponse.getData()) {
                        mListCategory.add(category);
                    }
                }
                mCategoryDialog.setData(mListCategory);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: Get category failed!");
            }
        });
    }

    @Override
    public void onCategoryClick(Category category) {
        mCategoryDialog.dismiss();
        mTvCategory.setText(category.getCategoryName());
        if (category.getId() == 0) {
            getPlaceAround();
        } else {
            getPlaceAroundByCategory(category.getId());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.tvCategory:
                mCategoryDialog.show(getFragmentManager(), CategoryDialog.class.getSimpleName());
                break;
        }
    }

    @Override
    public void onPlaceClick(int position) {
        Intent intent = new Intent(this, DetailPlaceActivity.class);
        intent.putExtra(PLACE_ID_KEY, mListPlace.get(position).getId());
        startActivity(intent);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
            mGoogleMap.setOnInfoWindowClickListener(this);
            getPlaceAround();
        }
    }

    public void getPlaceAround() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mCurrentLocation = location;
                            if (mGoogleMap != null) {
                                mGoogleMap.clear();
                                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()),
                                        13f));
                                LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                                MarkerOptions options = new MarkerOptions();
                                options.title("Bạn đang ở đây!");
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                options.position(currentLatLng);
                                CustomMakerMap customMakerMap = new CustomMakerMap(ListPlaceAroundActivity.this);
                                mGoogleMap.setInfoWindowAdapter(customMakerMap);
                                mGoogleMap.addMarker(options);
                                MyRetrofit.getInstance().getService().getSearchAround(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()).enqueue(new Callback<PlaceAroundResponse>() {
                                    @Override
                                    public void onResponse(Call<PlaceAroundResponse> call, Response<PlaceAroundResponse> response) {
                                        PlaceAroundResponse placeResponse = response.body();
                                        mListPlace.clear();
                                        if (!placeResponse.getData().isEmpty()) {
                                            for (PlaceAround place : placeResponse.getData()) {
                                                mListPlace.add(place);
                                            }
                                            for (int i = 0; i < mListPlace.size(); i++) {
                                                MarkerOptions markerOptions = new MarkerOptions();
                                                markerOptions.position(new LatLng(mListPlace.get(i).getLatitude(), mListPlace.get(i).getLongitude()))
                                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                                Marker marker = mGoogleMap.addMarker(markerOptions);
                                                marker.setTag(mListPlace.get(i));
                                            }
                                        }
                                        mListPlaceAroundAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<PlaceAroundResponse> call, Throwable t) {
                                        Log.d("xxx", "onFailure: Get Place failed!");
                                    }
                                });

                            }
                        }
                    }
                });
    }

    public void getPlaceAroundByCategory(final int id) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mCurrentLocation = location;
                            if (mGoogleMap != null) {
                                mGoogleMap.clear();
                                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()),
                                        13f));
                                LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                                MarkerOptions options = new MarkerOptions();
                                options.title("Bạn đang ở đây!");
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                options.position(currentLatLng);
                                CustomMakerMap customMakerMap = new CustomMakerMap(ListPlaceAroundActivity.this);
                                mGoogleMap.setInfoWindowAdapter(customMakerMap);
                                mGoogleMap.addMarker(options);
                                MyRetrofit.getInstance().getService().getSearchAroundByCategory(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), id).enqueue(new Callback<PlaceAroundResponse>() {
                                    @Override
                                    public void onResponse(Call<PlaceAroundResponse> call, Response<PlaceAroundResponse> response) {
                                        PlaceAroundResponse placeResponse = response.body();
                                        mListPlace.clear();
                                        if (!placeResponse.getData().isEmpty()) {
                                            for (PlaceAround place : placeResponse.getData()) {
                                                mListPlace.add(place);
                                            }
                                            for (int i = 0; i < mListPlace.size(); i++) {
                                                MarkerOptions markerOptions = new MarkerOptions();
                                                markerOptions.position(new LatLng(mListPlace.get(i).getLatitude(), mListPlace.get(i).getLongitude()))
                                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                                Marker marker = mGoogleMap.addMarker(markerOptions);
                                                marker.setTag(mListPlace.get(i));
                                            }
                                        }
                                        mListPlaceAroundAdapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onFailure(Call<PlaceAroundResponse> call, Throwable t) {
                                        Log.d("xxx", "onFailure: Get Place failed!");
                                    }
                                });

                            }
                        }
                    }
                });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Object place = marker.getTag();
        if (place != null) {
            PlaceAround placeAround = (PlaceAround) place;
            if (placeAround.getId() != null) {
                Intent intent = new Intent(this, DetailPlaceActivity.class);
                intent.putExtra(PLACE_ID_KEY, placeAround.getId());
                startActivity(intent);
            }
        }
    }
}
