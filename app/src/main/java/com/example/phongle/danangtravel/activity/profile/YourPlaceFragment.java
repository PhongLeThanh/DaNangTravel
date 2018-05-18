package com.example.phongle.danangtravel.activity.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phongle.danangtravel.R;

public class YourPlaceFragment extends Fragment implements View.OnClickListener{

    public static Fragment getInstance() {
        return new YourPlaceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list_your_place, container, false);
        initViews(view);
        initListener();
        return view;
    }
    private void initViews(View view){

    }
    private void initListener(){

    }
    @Override
    public void onClick(View v) {

    }
}
