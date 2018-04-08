package com.example.phongle.danangtravel.activity.headerHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;

/**
 * Created by phongle on 30/3/2561.
 * Adapter for spinner choice district
 */

public class DistrictSpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mListDistrict;

    public DistrictSpinnerAdapter(Context mContext, String[] mListDistrict) {
        this.mContext = mContext;
        this.mListDistrict = mListDistrict;
    }

    @Override
    public int getCount() {
        return mListDistrict.length;
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
        tvDistrict.setText(mListDistrict[position]);
        return view;
    }
}
