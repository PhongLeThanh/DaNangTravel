package com.example.phongle.danangtravel.activity.list;

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
import com.example.phongle.danangtravel.utils.ReWriteUrl;
import com.example.phongle.danangtravel.models.TouristAttraction;

import java.util.List;

public class ListAttractionAdapter extends RecyclerView.Adapter<ListAttractionAdapter.ListAttractionViewHolder> {
    private List<TouristAttraction> mListTourist;
    private onItemClickListener mOnItemClickListener;

    ListAttractionAdapter(List<TouristAttraction> listPlace, onItemClickListener onItemClickListener) {
        mListTourist = listPlace;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ListAttractionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_place, parent, false);
        return new ListAttractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAttractionViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListTourist == null ? 0 : mListTourist.size();
    }

    public interface onItemClickListener {
        void onPlaceClick(int postion);
    }

    class ListAttractionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mLlItemListActraction;
        private ImageView mImgAttraction;
        private TextView mTvAttractionName;
        private TextView mTvDescriptionPlace;
        private RatingBar mRatingAttraction;
        private TextView mTvNumCommentAtraction;
        private LinearLayout mLlCost;

        ListAttractionViewHolder(final View itemView) {
            super(itemView);
            initViews();
            initListener();
        }

        private void initViews() {
            mLlItemListActraction = itemView.findViewById(R.id.llItemPlace);
            mImgAttraction = itemView.findViewById(R.id.imgPlace);
            mTvAttractionName = itemView.findViewById(R.id.tvPlaceName);
            mTvDescriptionPlace = itemView.findViewById(R.id.tvDescriptionPlace);
            mRatingAttraction = itemView.findViewById(R.id.ratingPlace);
            mTvNumCommentAtraction = itemView.findViewById(R.id.tvNumComment);
            mLlCost = itemView.findViewById(R.id.llCost);
        }

        private void initListener() {
            mLlItemListActraction.setOnClickListener(this);
        }

        private void onBindData() {
            TouristAttraction place = mListTourist.get(getAdapterPosition());
            if (place.getImages() != null && place.getImages().get(0).getImageName() != null) {
                Glide.with(mImgAttraction.getContext()).load(ReWriteUrl.reWriteUrl(place.getImages().get(0).getImageName()))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                mImgAttraction.setImageResource(R.drawable.bg_default);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(mImgAttraction);
            } else {
                mImgAttraction.setImageResource(R.drawable.bg_default);
            }
            mTvAttractionName.setText(place.getPlaceName());
            mTvDescriptionPlace.setText(place.getDescription());
            mRatingAttraction.setRating(place.getRating());
            mTvNumCommentAtraction.setText((String.valueOf(place.getNumcomment())));
            for (int i = 0; i < mLlCost.getChildCount(); i++) {
                View view = mLlCost.getChildAt(i);
                view.setVisibility(View.GONE);
            }
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
