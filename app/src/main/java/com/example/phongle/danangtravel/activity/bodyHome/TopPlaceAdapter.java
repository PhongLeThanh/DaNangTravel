package com.example.phongle.danangtravel.activity.bodyHome;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.Place;

import java.util.List;

public class TopPlaceAdapter extends RecyclerView.Adapter<TopPlaceAdapter.TopPlaceViewHolder> {
    private List<Place> mListPlace;
    private onItemClickListener mOnItemClickListener;

    public TopPlaceAdapter(List<Place> listPlace, onItemClickListener onItemClickListener) {
        mListPlace = listPlace;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public TopPlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_place, parent, false);
        return new TopPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopPlaceViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListPlace.size();
    }

    class TopPlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mLlItemPlace;
        private ImageView mImgPlace;
        private TextView mTvPlaceName;
        private RatingBar mRatingPlace;
        private TextView mTvNumComment;

        TopPlaceViewHolder(final View itemView) {
            super(itemView);
            initViews();
            initListener();
        }

        private void initViews() {
            mLlItemPlace = itemView.findViewById(R.id.llItemTopPlace);
            mImgPlace = itemView.findViewById(R.id.imgTopPlace);
            mTvPlaceName = itemView.findViewById(R.id.tvTopPlaceName);
            mRatingPlace = itemView.findViewById(R.id.ratingPlace);
            mTvNumComment = itemView.findViewById(R.id.tvNumComment);
        }

        private void initListener() {
            mLlItemPlace.setOnClickListener(this);
        }

        private void onBindData() {
            Place place = mListPlace.get(getAdapterPosition());
            mImgPlace.setImageResource(place.getImage());
            mTvPlaceName.setText(place.getName());
            mRatingPlace.setRating(place.getRating());
            mTvNumComment.setText((String.valueOf(place.getNumComment())));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llItemTopPlace:
                    mOnItemClickListener.onPlaceClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface onItemClickListener {
        void onPlaceClick(int postion);
    }
}
