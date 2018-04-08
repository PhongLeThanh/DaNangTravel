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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_restaurant, parent, false);
        return new TopRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopRestaurantViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListRestaurant.size();
    }

    class TopRestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mLlItemRestaurant;
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
            mLlItemRestaurant = itemView.findViewById(R.id.llItemTopRestaurant);
            mImgRestaurant = itemView.findViewById(R.id.imgTopRestaurant);
            mTvRestaurantName = itemView.findViewById(R.id.tvTopRestaurantName);
            mRatingRestaurant = itemView.findViewById(R.id.ratingRestaurant);
            mTvNumCommentRestaurant = itemView.findViewById(R.id.tvNumCommentRestaurant);
        }

        private void initListener() {
            mLlItemRestaurant.setOnClickListener(this);
        }

        private void onBindData() {
            Restaurant restaurant = mListRestaurant.get(getAdapterPosition());
            mImgRestaurant.setImageResource(restaurant.getImage());
            mTvRestaurantName.setText(restaurant.getName());
            mRatingRestaurant.setRating(restaurant.getRating());
            mTvNumCommentRestaurant.setText((String.valueOf(restaurant.getNumComment())));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llItemTopRestaurant:
                    mOnItemClickListener.onRestaurantClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface onItemClickListener {
        void onRestaurantClick(int postion);
    }
}
