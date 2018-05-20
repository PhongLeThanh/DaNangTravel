package com.example.phongle.danangtravel.activity.aroundhere;

import android.annotation.SuppressLint;
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
import com.example.phongle.danangtravel.models.PlaceAround;

import java.util.List;

public class ListPlaceAroundAdapter extends RecyclerView.Adapter<ListPlaceAroundAdapter.ListPlaceAroundViewHolder> {
    private List<PlaceAround> mListPlace;
    private OnItemClickListener mOnItemClickListener;

    ListPlaceAroundAdapter(List<PlaceAround> listPlace, OnItemClickListener onItemClickListener) {
        mListPlace = listPlace;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ListPlaceAroundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_place_around, parent, false);
        return new ListPlaceAroundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListPlaceAroundViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListPlace == null ? 0 : mListPlace.size();
    }

    public interface OnItemClickListener {
        void onPlaceClick(int position);
    }

    class ListPlaceAroundViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mLlItemListPlace;
        private ImageView mImgPlace;
        private TextView mTvPlaceName;
        private TextView mTvDescriptionPlace;
        private RatingBar mRatingPlace;
        private TextView mTvNumCommentPlace;
        private TextView mTvDistance;

        ListPlaceAroundViewHolder(final View itemView) {
            super(itemView);
            initViews();
            initListener();
        }

        private void initViews() {
            mLlItemListPlace = itemView.findViewById(R.id.llItemPlace);
            mImgPlace = itemView.findViewById(R.id.imgPlace);
            mTvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            mTvDescriptionPlace = itemView.findViewById(R.id.tvDescriptionPlace);
            mRatingPlace = itemView.findViewById(R.id.ratingPlace);
            mTvNumCommentPlace = itemView.findViewById(R.id.tvNumComment);
            mTvDistance = itemView.findViewById(R.id.tvDistance);
        }

        private void initListener() {
            mLlItemListPlace.setOnClickListener(this);
        }

        @SuppressLint("DefaultLocale")
        private void onBindData() {
            PlaceAround place = mListPlace.get(getAdapterPosition());
            if (place.getPicture() != null) {
                Glide.with(mImgPlace.getContext()).load(ReWriteUrl.reWriteUrl(place.getPicture()))
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
            mTvPlaceName.setText(place.getPlaceName());
            mTvDescriptionPlace.setText(place.getDescription());
            mRatingPlace.setRating(place.getRating());
            mTvNumCommentPlace.setText((String.valueOf(place.getNumcomment())));
            mTvDistance.setText(String.format("%.3f", place.getDistance()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llItemPlace:
                    mOnItemClickListener.onPlaceClick(getAdapterPosition());
                    break;
            }
        }
    }
}
