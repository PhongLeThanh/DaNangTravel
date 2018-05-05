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
import com.squareup.picasso.Picasso;

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
        return mListRestaurant.size();
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
            mTvRestaurantName = itemView.findViewById(R.id.tvTopPlaceName);
            mRatingRestaurant = itemView.findViewById(R.id.ratingPlace);
            mTvNumCommentRestaurant = itemView.findViewById(R.id.tvNumComment);
        }

        private void initListener() {
            mLlItemRestaurant.setOnClickListener(this);
        }

        private void onBindData() {
            Restaurant restaurant = mListRestaurant.get(getAdapterPosition());
            if (restaurant.getImages() != null && restaurant.getImages().size() >0 && restaurant.getImages().get(0).getImageName() !=null){
                Picasso.with(mImgRestaurant.getContext())
                        .load(restaurant.getImages().get(0).getImageName())
                        .error(R.drawable.bg_restaurant)
                        .into(mImgRestaurant);
            }
            mTvRestaurantName.setText(restaurant.getPlaceName());
            mRatingRestaurant.setRating(restaurant.getRating());
            mTvNumCommentRestaurant.setText((String.valueOf(restaurant.getNumComment())));
            for (int i = 0; i < mLlCost.getChildCount(); i++) {
                View view = mLlCost.getChildAt(i);
                view.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llCost:
                    mOnItemClickListener.onRestaurantClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface onItemClickListener {
        void onRestaurantClick(int postion);
    }
}
