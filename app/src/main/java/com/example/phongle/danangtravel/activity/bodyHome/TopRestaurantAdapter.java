package com.example.phongle.danangtravel.activity.bodyHome;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.utils.ReWriteUrl;
import com.example.phongle.danangtravel.models.Restaurant;

import java.util.List;

public class TopRestaurantAdapter extends RecyclerView.Adapter<TopRestaurantAdapter.TopRestaurantViewHolder> {
    private List<Restaurant> mListRestaurant;
    private onItemClickListener mOnItemClickListener;

    public TopRestaurantAdapter(List<Restaurant> listRestaurant, onItemClickListener onItemClickListener) {
        mListRestaurant = listRestaurant;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public TopRestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_place, parent, false);
        return new TopRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopRestaurantViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListRestaurant ==null ? 0 :mListRestaurant.size();
    }

    class TopRestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mLlItemRestaurant;
        private LinearLayout mLlCost;
        private ImageView mImgRestaurant;
        private TextView mTvRestaurantName;
        private RatingBar mRatingRestaurant;
        private TextView mTvNumCommentRestaurant;

        TopRestaurantViewHolder(final View itemView) {
            super(itemView);
            initViews();
            initListener();
        }

        private void initViews() {
            mLlItemRestaurant = itemView.findViewById(R.id.llItemTopPlace);
            mLlCost = itemView.findViewById(R.id.llCost);
            mImgRestaurant = itemView.findViewById(R.id.imgTopPlace);
            mTvRestaurantName = itemView.findViewById(R.id.tvPlaceName);
            mRatingRestaurant = itemView.findViewById(R.id.ratingPlace);
            mTvNumCommentRestaurant = itemView.findViewById(R.id.tvNumComment);
        }

        private void initListener() {
            mLlItemRestaurant.setOnClickListener(this);
        }

        private void onBindData() {
            Restaurant restaurant = mListRestaurant.get(getAdapterPosition());
            if (restaurant.getImages() != null && restaurant.getImages().size() >0 && restaurant.getImages().get(0).getImageName() !=null){
                Glide.with(mImgRestaurant.getContext()).load(ReWriteUrl.reWriteUrl(restaurant.getImages().get(0).getImageName()))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                mImgRestaurant.setImageResource(R.drawable.bg_default);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(mImgRestaurant);
            }else {
                mImgRestaurant.setImageResource(R.drawable.bg_default);
            }
            mTvRestaurantName.setText(restaurant.getPlaceName());
            mRatingRestaurant.setRating(restaurant.getRating());
            mTvNumCommentRestaurant.setText((String.valueOf(restaurant.getNumcomment())));
            for (int i = 0; i < mLlCost.getChildCount(); i++) {
                View view = mLlCost.getChildAt(i);
                view.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llItemTopPlace:
                    mOnItemClickListener.onRestaurantClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface onItemClickListener {
        void onRestaurantClick(int position);
    }
}
