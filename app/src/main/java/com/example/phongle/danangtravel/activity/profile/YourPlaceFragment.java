package com.example.phongle.danangtravel.activity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.detail.DetailPlaceActivity;
import com.example.phongle.danangtravel.api.LikePlaceResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.PlaceResponse;
import com.example.phongle.danangtravel.models.Place;
import com.example.phongle.danangtravel.models.User;
import com.example.phongle.danangtravel.models.shareds.SharedPrefeencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourPlaceFragment extends Fragment implements YourPlaceAdapter.OnItemClickListener {
    private static final String PLACE_ID_KEY = "id";
    private RecyclerView mRecyclerViewYourPlace;
    private YourPlaceAdapter mYourPlaceAdapter;
    private List<Place> mListPlace = new ArrayList<>();

    public static Fragment getInstance() {
        return new YourPlaceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list_your_place, container, false);
        initViews(view);
        initAdapter();
        initData();
        return view;
    }

    private void initViews(View view) {
        mRecyclerViewYourPlace = view.findViewById(R.id.recyclerViewListYourPlace);
    }

    private void initAdapter() {
        mYourPlaceAdapter = new YourPlaceAdapter(getContext(), mListPlace, this);
        mRecyclerViewYourPlace.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewYourPlace.setAdapter(mYourPlaceAdapter);
    }

    private void setDislikePlace(int userId, int placeId) {
        String token = "Bearer " + SharedPrefeencesUtils.getDocument();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("placeId", placeId);
            jsonObject.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        MyRetrofit.getInstance().getService().disLikePlace(token, requestBody).enqueue(new Callback<LikePlaceResponse>() {
            @Override
            public void onResponse(Call<LikePlaceResponse> call, Response<LikePlaceResponse> response) {
                LikePlaceResponse likePlaceResponse = response.body();
                if (likePlaceResponse != null) {
                    initData();
                }
            }

            @Override
            public void onFailure(Call<LikePlaceResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: failed");
            }
        });
    }

    private void initData() {
        User user = SharedPrefeencesUtils.getUser();
        if (user != null) {
            getLikePlace(user);
        }
    }

    private void getLikePlace(User user) {
        MyRetrofit.getInstance().getService().getPlaceByUserId(user.getId()).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                PlaceResponse placeResponse = response.body();
                mListPlace.clear();
                if (placeResponse != null && !placeResponse.getData().isEmpty()) {
                    mListPlace.addAll(placeResponse.getData());
                } else {
                    Toast.makeText(getContext(), "Bạn chưa thích địa điểm nào !", Toast.LENGTH_LONG).show();
                }
                mYourPlaceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: failed");
            }
        });
    }

    @Override
    public void onPlaceClick(int position) {
        Intent intent = new Intent(getContext(), DetailPlaceActivity.class);
        intent.putExtra(PLACE_ID_KEY, mListPlace.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onPlaceLongClick(int position) {
        User user = SharedPrefeencesUtils.getUser();
        setDislikePlace(user.getId(), mListPlace.get(position).getId());
    }
}
