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
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.PlaceResponse;
import com.example.phongle.danangtravel.models.Place;
import com.example.phongle.danangtravel.models.User;
import com.example.phongle.danangtravel.models.shareds.SharedPrefeencesUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourPlaceFragment extends Fragment implements YourPlaceAdapter.OnItemClickListener{
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
    private void initViews(View view){
        mRecyclerViewYourPlace = view.findViewById(R.id.recyclerViewListYourPlace);
    }
    private void initAdapter(){
        mYourPlaceAdapter = new YourPlaceAdapter(mListPlace,this);
        mRecyclerViewYourPlace.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewYourPlace.setAdapter(mYourPlaceAdapter);
    }
    private void initData(){
        User user = SharedPrefeencesUtils.getUser();
        MyRetrofit.getInstance().getService().getPlaceByUserId(user.getId()).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                PlaceResponse placeResponse = response.body();
                if(placeResponse!=null && !placeResponse.getData().isEmpty()){
                    mListPlace.clear();
                    mListPlace.addAll(placeResponse.getData());
                    mYourPlaceAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(),"Bạn chưa thích địa điểm nào !",Toast.LENGTH_LONG).show();
                }
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
}
