package com.example.phongle.danangtravel.activity.detail;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.utils.ReWriteUrl;
import com.example.phongle.danangtravel.models.Image;

import java.util.List;

/**
 * Created by phongle on 15/4/2018.
 * Adapter for Header viewpager activity DetailPlace
 */

public class HeaderDetailAdapter extends PagerAdapter {
    private Context mContext;
    private List<Image> mListImage ;

    public HeaderDetailAdapter(Context mContext, List<Image> mListImage) {
        this.mContext = mContext;
        this.mListImage = mListImage;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_image_detail_place, container, false);
        ImageView mImgPlace = view.findViewById(R.id.imgDetailPlace);
        if(mListImage.size() > 0 && mListImage.get(position).getImageName()!= null) {
            Glide.with(mImgPlace.getContext()).load(ReWriteUrl.reWriteUrl(mListImage.get(position).getImageName()))
                    .into(mImgPlace);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(mListImage !=null && mListImage.size() > 0 && mListImage.size() <= 3 ){
            return mListImage.size();
        }else{
            return 3;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
