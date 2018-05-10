package com.example.phongle.danangtravel.activity.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPlaceActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, CommentDialogFragment.DialogCommentListener {
    private static final String PLACE_ID_KEY = "id";
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
    private List<Comment> mListComment = new ArrayList<>();

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
                    Place place = placeResponse.getData().get(0);
                    setViews(place);
                    if (place.getCategoryId() == 1) {
                        Restaurant restaurant = place.getRestaurant();
                        restaurant.setPlace(place);
                        mTvWebsite.setText("Website : " + restaurant.getWebsite());
                        mTvTime.setText("Giờ mở cửa : " + restaurant.getTime());
                        mTvDetail.setText("Mô tả : " + restaurant.getDetail());
                        mTvMoreInfo.setText("Thông tin thêm : " + restaurant.getMoreInformation());
                    }
                    if (place.getCategoryId() == 2) {
                        Hotel hotel = place.getHotel();
                        hotel.setPlace(place);
                        mTvCost.setVisibility(View.VISIBLE);
                        mTvCost.setText(String.valueOf(hotel.getCost()) + "   vnd/Đêm ");
                        mTvWebsite.setText("Website : " + hotel.getWebsite());
                        mTvTime.setText("Giờ mở cửa : Luôn mở cửa !");
                        mTvDetail.setText("Mô tả : " + hotel.getDetail());
                        mTvMoreInfo.setText("Thông tin thêm : " + hotel.getMoreInformation());
                    }
                    if (place.getCategoryId() == 3) {
                        TouristAttraction touristAttraction = place.getTouristattraction();
                        touristAttraction.setPlace(place);
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
        mCollapsingToolbarLayout.setTitle(place.getPlaceName());
        mTvPlaceName.setText(place.getPlaceName());
        mRatingPlace.setRating(place.getRating());
        mTvNumComment.setText(String.valueOf(place.getNumComment()));
        mTvAddress.setText(place.getAddress());
        mTvPhone.setText(place.getPhone());
        mTvDetail.setText(place.getDetail());
    }

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
                Log.d("xxx", "onFailure: comment failed"+t.getMessage());
            }
        });
    }
}