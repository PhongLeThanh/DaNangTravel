package com.example.phongle.danangtravel.activity.headerHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.Location;

import java.util.List;

/**
 * Created by phongle on 30/3/2561.
 * Adapter for spinner choice district
 */

public class DistrictSpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private List<Location> mListLocation;

    public DistrictSpinnerAdapter(Context mContext, List<Location> listLocation) {
        this.mContext = mContext;
        this.mListLocation = listLocation;
    }

    @Override
    public int getCount() {
        return mListLocation.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_spinner_district,container, false);
        TextView tvDistrict = view.findViewById(R.id.tvDistrict);
        tvDistrict.setText(mListLocation.get(position).getLocationName());
        return view;
    }
}
