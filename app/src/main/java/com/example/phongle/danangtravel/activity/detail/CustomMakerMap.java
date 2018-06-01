package com.example.phongle.danangtravel.activity.detail;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.PlaceAround;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomMakerMap implements GoogleMap.InfoWindowAdapter{
    private Context mContext;

    public CustomMakerMap(Context context){
        mContext = context;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)mContext).getLayoutInflater()
                .inflate(R.layout.custom_maker_map, null);

        RelativeLayout rlName = view.findViewById(R.id.rlContain);
        RelativeLayout rlDistance = view.findViewById(R.id.rlDistance);
        TextView tvName = view.findViewById(R.id.tvPlaceName);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvDistance = view.findViewById(R.id.tvDistance);
        TextView tvDetail = view.findViewById(R.id.tvDetailMap);
        TextView tvYou = view.findViewById(R.id.tvYouAreHere);

        PlaceAround placeAround = (PlaceAround) marker.getTag();
        if(placeAround !=null ){
            tvName.setText(placeAround.getPlaceName());
            tvAddress.setText(placeAround.getAddress());
            tvDistance.setText(String.format("%.3f", placeAround.getDistance()));
            tvDetail.setTag(placeAround.getId());
            tvYou.setVisibility(View.GONE);
        }else {
            rlName.setVisibility(View.GONE);
            rlDistance.setVisibility(View.GONE);
            tvYou.setVisibility(View.VISIBLE);
        }

        return view;
    }

}
