package com.example.phongle.danangtravel.activity.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.Restaurant;

import java.util.List;

public class ListRestaurantAdapter extends RecyclerView.Adapter<ListRestaurantAdapter.ListRestaurantViewHolder> {
    private List<Restaurant> mListRestaurant;
    private onItemClickListener mOnItemClickListener;

    public ListRestaurantAdapter(List<Restaurant> listRestaurant, onItemClickListener onItemClickListener) {
        mListRestaurant = listRestaurant;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ListRestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_place, parent, false);
        return new ListRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListRestaurantViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListRestaurant == null ? 0 : mListRestaurant.size();
    }

    public interface onItemClickListener {
        void onPlaceClick(int postion);
    }

    class ListRestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mLlItemListRestaurant;
        private ImageView mImgRestaurant;
        private TextView mTvRestaurantName;
        private TextView mTvDescriptionPlace;
        private RatingBar mRatingRestaurant;
        private TextView mTvNumCommentRestaurant;
        private LinearLayout mLlCost;

        ListRestaurantViewHolder(final View itemView) {
            super(itemView);
            initViews();
            initListener();
        }

        private void initViews() {
            mLlItemListRestaurant = itemView.findViewById(R.id.llItemPlace);
            mImgRestaurant = itemView.findViewById(R.id.imgPlace);
            mTvRestaurantName = itemView.findViewById(R.id.tvPlaceName);
            mTvDescriptionPlace = itemView.findViewById(R.id.tvDescriptionPlace);
            mRatingRestaurant = itemView.findViewById(R.id.ratingPlace);
            mTvNumCommentRestaurant = itemView.findViewById(R.id.tvNumComment);
            mLlCost = itemView.findViewById(R.id.llCost);
        }

        private void initListener() {
            mLlItemListRestaurant.setOnClickListener(this);
        }

        private void onBindData() {
            Restaurant restaurant = mListRestaurant.get(getAdapterPosition());
            mImgRestaurant.setImageResource(R.drawable.bg_restaurant);
            mTvRestaurantName.setText(restaurant.getPlaceName());
            mTvDescriptionPlace.setText(restaurant.getDescription());
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
                case R.id.llItemPlace:
                    mOnItemClickListener.onPlaceClick(getAdapterPosition());
                    break;
            }
        }
    }
}
