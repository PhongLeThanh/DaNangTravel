package com.example.phongle.danangtravel.activity.headerHome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.phongle.danangtravel.R;

/**
 * Created by phongle on 29/3/2561.
 * Adapter for Header viewpager activity
 */

public class HeaderAdapter extends PagerAdapter {
    private Context mContext;
    private int mListImage[];

    public HeaderAdapter(Context mContext, int[] mListImage) {
        this.mContext = mContext;
        this.mListImage = mListImage;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_image_header_home, container, false);
        LinearLayout llHeaderHome = view.findViewById(R.id.llHeaderHome);
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
