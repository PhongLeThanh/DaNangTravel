package com.example.phongle.danangtravel.activity.detail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.login.LoginActivity;
import com.example.phongle.danangtravel.activity.map.DirectionsJSONParser;
import com.example.phongle.danangtravel.api.CommentResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.PlaceResponse;
import com.example.phongle.danangtravel.models.Comment;
import com.example.phongle.danangtravel.models.Hotel;
import com.example.phongle.danangtravel.models.Place;
import com.example.phongle.danangtravel.models.Restaurant;
import com.example.phongle.danangtravel.models.TouristAttraction;
import com.example.phongle.danangtravel.models.User;
import com.example.phongle.danangtravel.models.shareds.SharedPrefeencesUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gs.maps.nestedscroll.SupportNestedScrollMapFragment;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPlaceActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener, CommentDialogFragment.DialogCommentListener,
        OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {
    private static final String PLACE_ID_KEY = "id";
    ArrayList markerPoints = new ArrayList();
    private GoogleMap mGoogleMap;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbarDetail;
    private ImageView mImgBack;
    private TextView mTvPlaceName;
    private RatingBar mRatingPlace;
    private TextView mTvNumComment;
    private TextView mTvAddress;
    private TextView mTvPhone;
    private TextView mTvCost;
    private CircleImageView mImgAvatarUser;
    private RatingBar mRatingCommentPlace;
    private TextView mTvWebsite;
    private TextView mTvTime;
    private TextView mTvDetail;
    private TextView mTvMoreInfo;
    private HeaderDetailAdapter mHeaderDetailAdapter;
    private int mListImage[] = {R.drawable.bg_detail_place, R.drawable.bg_detail_place_1, R.drawable.bg_detail_place_2};
    private RecyclerView mRecyclerViewComment;
    private ListCommentAdapter mListCommentAdapter;
    private Place mPlace = new Place();
    private List<Comment> mListComment = new ArrayList<>();
    private android.location.Location mCurrentLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        initViews();
        initListener();
        updateViews();
        initAdapter();
        initCircleIndicator();
        setSupportActionBar(mToolbarDetail);
    }

    private void initViews() {
        mCollapsingToolbarLayout = findViewById(R.id.detailCollapsingToolbar);
        mImgBack = findViewById(R.id.imgBack);
        mTvPlaceName = findViewById(R.id.tvPlaceName);
        mRatingPlace = findViewById(R.id.ratingPlace);
        mTvNumComment = findViewById(R.id.tvNumCommentPlace);
        mTvAddress = findViewById(R.id.tvAddressPlace);
        mTvPhone = findViewById(R.id.tvPhonePlace);
        mTvCost = findViewById(R.id.tvCost);
        mImgAvatarUser = findViewById(R.id.imgAvatarUser);
        mRatingCommentPlace = findViewById(R.id.ratingCommentPlace);
        mTvWebsite = findViewById(R.id.tvWebsitePlace);
        mTvTime = findViewById(R.id.tvTimePlace);
        mTvDetail = findViewById(R.id.tvDetailPlace);
        mTvMoreInfo = findViewById(R.id.tvMoreInfoPlace);
        mViewPager = findViewById(R.id.viewPagerDetailPlace);
        mToolbarDetail = findViewById(R.id.toolbarDetailPlace);
        mRecyclerViewComment = findViewById(R.id.recyclerViewComment);
    }

    private void updateViews() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(PLACE_ID_KEY, 0);
        if (SharedPrefeencesUtils.getDocument() != null) {
            mImgAvatarUser.setVisibility(View.VISIBLE);
            User user = SharedPrefeencesUtils.getUser();
            if (user.getAvatar() != null) {
                Picasso.with(mImgAvatarUser.getContext())
                        .load(user.getAvatar())
                        .error(R.drawable.bg_restaurant)
                        .into(mImgAvatarUser);
            } else {
                mImgAvatarUser.setImageResource(R.drawable.bg_avatar);
            }
        }
        MyRetrofit.getInstance().getService().getPlaceById(id).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                PlaceResponse placeResponse = response.body();
                if (!placeResponse.getData().isEmpty()) {
                    mPlace = placeResponse.getData().get(0);
                    double lat = mPlace.getLatitude();
                    setViews(mPlace);
                    if (mPlace.getCategoryId() == 1) {
                        Restaurant restaurant = mPlace.getRestaurant();
                        restaurant.setPlace(mPlace);
                        mTvWebsite.setText("Website : " + restaurant.getWebsite());
                        mTvTime.setText("Giờ mở cửa : " + restaurant.getTime());
                        mTvDetail.setText("Mô tả : " + restaurant.getDetail());
                        mTvMoreInfo.setText("Thông tin thêm : " + restaurant.getMoreInformation());
                    }
                    if (mPlace.getCategoryId() == 2) {
                        Hotel hotel = mPlace.getHotel();
                        hotel.setPlace(mPlace);
                        mTvCost.setVisibility(View.VISIBLE);
                        mTvCost.setText(String.valueOf(hotel.getCost()) + "   vnd/Đêm ");
                        mTvWebsite.setText("Website : " + hotel.getWebsite());
                        mTvTime.setText("Giờ mở cửa : Luôn mở cửa !");
                        mTvDetail.setText("Mô tả : " + hotel.getDetail());
                        mTvMoreInfo.setText("Thông tin thêm : " + hotel.getMoreInformation());
                    }
                    if (mPlace.getCategoryId() == 3) {
                        TouristAttraction touristAttraction = mPlace.getTouristattraction();
                        touristAttraction.setPlace(mPlace);
                        mTvCost.setVisibility(View.VISIBLE);
                        if (touristAttraction.getTicket() > 0) {
                            mTvCost.setText(String.valueOf(touristAttraction.getTicket()) + "   / vé ");
                        } else {
                            mTvCost.setText("Free");
                        }
                        mTvWebsite.setVisibility(View.GONE);
                        mTvTime.setText("Giờ mở cửa : ");
                        mTvDetail.setText("Mô tả : " + touristAttraction.getDetail());
                        mTvMoreInfo.setText("Thông tin thêm : " + touristAttraction.getMoreInformation());
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: " + "failed");
            }
        });
        MyRetrofit.getInstance().getService().getCommentByPlaceId(id).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                CommentResponse commentResponse = response.body();
                if (!commentResponse.getData().isEmpty()) {
                    for (Comment comment : commentResponse.getData()) {
                        mListComment.add(comment);
                    }
                    Log.d("xxx", "onResponse: " + mListComment.get(0).getContent());
                }
                mListCommentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: Failed");
            }
        });
    }

    private void setViews(Place place) {
        SupportNestedScrollMapFragment mapFragment
                = (SupportNestedScrollMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mCollapsingToolbarLayout.setTitle(place.getPlaceName());
        mTvPlaceName.setText(place.getPlaceName());
        mRatingPlace.setRating(place.getRating());
        mTvNumComment.setText(String.valueOf(place.getNumComment()));
        mTvAddress.setText(place.getAddress());
        mTvPhone.setText(place.getPhone());
        mTvDetail.setText(place.getDetail());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        mImgBack.setOnClickListener(this);
        mRatingCommentPlace.setOnTouchListener(this);
    }

    private void initAdapter() {
        // Set Adapter for view pager header detail
        mHeaderDetailAdapter = new HeaderDetailAdapter(this, mListImage);
        mViewPager.setAdapter(mHeaderDetailAdapter);
        // Set Adapter for recycler view Comment and rating
        mListCommentAdapter = new ListCommentAdapter(mListComment);
        mRecyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewComment.setAdapter(mListCommentAdapter);
    }

    private void initCircleIndicator() {
        CircleIndicator circleIndicator = findViewById(R.id.circleIndicatorDetail);
        circleIndicator.setViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.ratingCommentPlace:
                if (SharedPrefeencesUtils.getDocument() != null) {
                    CommentDialogFragment commentDialogFragment = new CommentDialogFragment();
                    commentDialogFragment.setDialogCommentListener(this);
                    commentDialogFragment.show(getFragmentManager().beginTransaction(), "1000");
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailPlaceActivity.this);
                    alertDialogBuilder.setTitle(getResources().getString(R.string.alert_require_login_title));
                    alertDialogBuilder
                            .setMessage(getResources().getString(R.string.alert_require_login))
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(DetailPlaceActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                break;
        }
        return false;
    }

    @Override
    public void onComment(int evaluate, String content) {
        Intent intent = getIntent();
        int placeId = intent.getIntExtra(PLACE_ID_KEY, 0);
        int userId = SharedPrefeencesUtils.getUser().getId();
        String token = "Bearer " + SharedPrefeencesUtils.getDocument();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("placeId", placeId);
            jsonObject.put("userId", userId);
            jsonObject.put("content", content);
            jsonObject.put("evaluate", evaluate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        MyRetrofit.getInstance().getService().postComment(token, requestBody).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                mListComment.clear();
                CommentResponse commentResponse = response.body();
                if (!commentResponse.getData().isEmpty()) {
                    for (Comment comment : commentResponse.getData()) {
                        mListComment.add(comment);
                    }
                    Log.d("xxx", "onResponse: " + mListComment.get(0).getContent());
                }
                mListCommentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: comment failed" + t.getMessage());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
            getCurrentLocation();
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (mCurrentLocation != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()),
                    14f));
        }
        return false;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public void getCurrentLocation() {
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
                                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()),
                                        13f));
                                if (mPlace != null && mCurrentLocation != null) {
                                    LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                                    LatLng placeLatLng = new LatLng(mPlace.getLatitude(), mPlace.getLongitude());
                                    markerPoints.add(currentLatLng);
                                    markerPoints.add(placeLatLng);
                                    MarkerOptions options = new MarkerOptions();
                                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                    options.position(currentLatLng);

                                    MarkerOptions option = new MarkerOptions();
                                    option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                    option.position(placeLatLng);
                                    mGoogleMap.addMarker(options);
                                    mGoogleMap.addMarker(option);
                                    if (markerPoints.size() >= 2) {
                                        LatLng origin = currentLatLng;
                                        LatLng dest = placeLatLng;

                                        // Getting URL to the Google Directions API
                                        String url = getDirectionsUrl(origin, dest);

                                        DownloadTask downloadTask = new DownloadTask();

                                        // Start downloading json data from Google Directions API
                                        downloadTask.execute(url);
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(R.color.red);
                lineOptions.geodesic(true);
            }
            mGoogleMap.addPolyline(lineOptions);
        }
    }
}