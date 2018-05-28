package com.example.phongle.danangtravel.activity.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.utils.ReWriteUrl;
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
        final ImageView mImgPlace = view.findViewById(R.id.imgDetailPlace);
        if(mListImage.size() > 0 && mListImage.get(position).getImageName()!= null) {
            Glide.with(mImgPlace.getContext()).load(ReWriteUrl.reWriteUrl(mListImage.get(position).getImageName()))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            mImgPlace.setImageResource(R.drawable.bg_default);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(mImgPlace);
        }else {
            mImgPlace.setImageResource(R.drawable.bg_default);
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
