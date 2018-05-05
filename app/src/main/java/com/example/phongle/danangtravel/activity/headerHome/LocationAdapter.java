package com.example.phongle.danangtravel.activity.headerHome;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<Location> mLocations;
    private OnLocationClickListener mOnLocationClickListener;

    public LocationAdapter() {
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        holder.mTvLocation.setText(mLocations.get(position).getLocationName());
    }

    @Override
    public int getItemCount() {
        return mLocations == null ? 0 : mLocations.size();
    }

    public void setLocations(List<Location> locations) {
        if (mLocations == null) {
            mLocations = new ArrayList<>();
        }
        if (locations != null && locations.size() > 0) {
            mLocations.clear();
            mLocations = locations;
            notifyDataSetChanged();
        }
    }

    public void setOnLocationClickListener(OnLocationClickListener onLocationClickListener) {
        mOnLocationClickListener = onLocationClickListener;
    }

    public interface OnLocationClickListener {
        void onLocationClick(Location location);
    }

    class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvLocation;

        public LocationViewHolder(View itemView) {
            super(itemView);
            mTvLocation = itemView.findViewById(R.id.tvLocation);
            mTvLocation.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnLocationClickListener != null) {
                mOnLocationClickListener.onLocationClick(mLocations.get(getAdapterPosition()));
            }
        }
    }
}
