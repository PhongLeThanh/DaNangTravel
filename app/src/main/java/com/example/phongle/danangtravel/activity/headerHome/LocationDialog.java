package com.example.phongle.danangtravel.activity.headerHome;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.Location;

import java.util.List;

public class LocationDialog extends DialogFragment {
    private RecyclerView mRecyclerLocation;
    private LocationAdapter mAdapter;

    public LocationDialog() {
        mAdapter = new LocationAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Choose Location:");
        getDialog().setCancelable(true);
        View view = inflater.inflate(R.layout.dialog_choose_location, container, false);
        mRecyclerLocation = view.findViewById(R.id.recyclerLocation);
        mRecyclerLocation.setAdapter(mAdapter);
        mRecyclerLocation.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    public void setData(List<Location> locations) {
        mAdapter.setLocations(locations);
    }

    public void setCallback(LocationAdapter.OnLocationClickListener onLocationClickListener) {
        mAdapter.setOnLocationClickListener(onLocationClickListener);
    }
}
