package com.example.phongle.danangtravel.activity.detail;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.phongle.danangtravel.R;

/**
 * Created by phongle on 15/4/2018.
 * Adapter for Header viewpager activity DetailPlace
 */

public class HeaderDetailAdapter extends PagerAdapter {
    private Context mContext;
    private int mListImage[];

    public HeaderDetailAdapter(Context mContext, int[] mListImage) {
        this.mContext = mContext;
        this.mListImage = mListImage;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_image_detail_place, container, false);
        LinearLayout llHeaderHome = view.findViewById(R.id.llHeaderDetailPlace);
        llHeaderHome.setBackgroundResource(mListImage[position]);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
