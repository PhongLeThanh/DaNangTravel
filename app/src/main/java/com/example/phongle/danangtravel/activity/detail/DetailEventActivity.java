package com.example.phongle.danangtravel.activity.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.login.LoginActivity;
import com.example.phongle.danangtravel.activity.utils.ReWriteUrl;
import com.example.phongle.danangtravel.api.EventResponse;
import com.example.phongle.danangtravel.api.LikeEventResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.models.Event;
import com.example.phongle.danangtravel.models.User;
import com.example.phongle.danangtravel.models.shareds.SharedPrefeencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EVENT_ID_KEY = "eventId";
    private Toolbar mToolbar;
    private ImageView mImgBack;
    private ImageView mImgLike;
    private ImageView mImgEvent;
    private TextView mTvEventName;
    private TextView mTvTimeStart;
    private TextView mTvTimeFinish;
    private TextView mTvAddress;
    private TextView mTvDetail;
    private TextView mTvNumLike;
    private Button mBtnSearch;
    private Event mEvent;
    private boolean mIsLikeChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        initViews();
        initListener();
        updateViews();
        setSupportActionBar(mToolbar);
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbarDetailEvent);
        mImgBack = findViewById(R.id.imgBack);
        mImgLike = findViewById(R.id.imgLike);
        mImgEvent = findViewById(R.id.imgDetailEvent);
        mTvEventName = findViewById(R.id.tvEventName);
        mTvTimeStart = findViewById(R.id.tvTimeStart);
        mTvTimeFinish = findViewById(R.id.tvTimeFinish);
        mTvAddress = findViewById(R.id.tvAddressEvent);
        mTvDetail = findViewById(R.id.tvDetailEvent);
        mTvNumLike = findViewById(R.id.tvNumLike);
        mBtnSearch = findViewById(R.id.btnSearchNearEvent);
    }

    private void initListener() {
        mImgBack.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
        mImgLike.setOnClickListener(this);
    }

    private void updateViews() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(EVENT_ID_KEY, 0);
        MyRetrofit.getInstance().getService().getEventById(id).enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse eventResponse = response.body();
                if (eventResponse != null && !eventResponse.getData().isEmpty()) {
                    mEvent = eventResponse.getData().get(0);
                    if (mEvent.getImage() != null) {
                        Glide.with(mImgEvent.getContext()).load(ReWriteUrl.reWriteUrl(mEvent.getImage()))
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        mImgEvent.setImageResource(R.drawable.bg_default);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                }).into(mImgEvent);
                    }
                    mTvEventName.setText(mEvent.getEventName());
                    mTvTimeStart.setText(mEvent.getStart().substring(0, 10));
                    mTvTimeFinish.setText(mEvent.getFinish().substring(0, 10));
                    mTvAddress.setText(mEvent.getAddress());
                    mTvDetail.setText(mEvent.getDetail());
                    mTvNumLike.setText(mEvent.getNumlike());
                    if (SharedPrefeencesUtils.getDocument() == null) {
                        mImgLike.setSelected(false);
                    } else {
                        User user = SharedPrefeencesUtils.getUser();
                        for (int i = 0; i < mEvent.getLikeevents().size(); i++) {
                            if (mEvent.getLikeevents().get(i).getUserId() == user.getId()) {
                                mImgLike.setSelected(true);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: failed");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.imgLike:
                if (SharedPrefeencesUtils.getDocument() == null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailEventActivity.this);
                    alertDialogBuilder.setTitle(getResources().getString(R.string.alert_require_login_title));
                    alertDialogBuilder
                            .setMessage(getResources().getString(R.string.alert_require_login))
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(DetailEventActivity.this, LoginActivity.class);
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
                } else {
                    User user = SharedPrefeencesUtils.getUser();
                    if (mImgLike.isSelected()) {
                        setDislikeEvent(user.getId(), mEvent.getId());
                    } else {
                        setLikeEvent(user.getId(), mEvent.getId());
                    }
                }
                break;
            case R.id.btnSearchNearEvent:
                break;
        }
    }

    private void setLikeEvent(int userId, int eventId) {
        String token = "Bearer " + SharedPrefeencesUtils.getDocument();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventId", eventId);
            jsonObject.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        MyRetrofit.getInstance().getService().like(token, requestBody).enqueue(new Callback<LikeEventResponse>() {
            @Override
            public void onResponse(Call<LikeEventResponse> call, Response<LikeEventResponse> response) {
                LikeEventResponse likeEventResponse = response.body();
                if (likeEventResponse != null) {
                    mImgLike.setSelected(true);
                }
            }

            @Override
            public void onFailure(Call<LikeEventResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: failed");
            }
        });
    }

    private void setDislikeEvent(int userId, int eventId) {
        String token = "Bearer " + SharedPrefeencesUtils.getDocument();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventId", eventId);
            jsonObject.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        MyRetrofit.getInstance().getService().disLike(token, requestBody).enqueue(new Callback<LikeEventResponse>() {
            @Override
            public void onResponse(Call<LikeEventResponse> call, Response<LikeEventResponse> response) {
                LikeEventResponse likeEventResponse = response.body();
                if (likeEventResponse != null) {
                    mImgLike.setSelected(false);
                    mIsLikeChange = true;
                }
            }

            @Override
            public void onFailure(Call<LikeEventResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: failed");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(mIsLikeChange){
            setResult(RESULT_OK);
            finish();
        }else {
            super.onBackPressed();
        }
    }
}
