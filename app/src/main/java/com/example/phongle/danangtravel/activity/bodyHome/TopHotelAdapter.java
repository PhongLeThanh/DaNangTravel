package com.example.phongle.danangtravel.activity.bodyHome;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.Hotel;

import java.util.List;

public class TopHotelAdapter extends RecyclerView.Adapter<TopHotelAdapter.TopHotelViewHolder> {
    private List<Hotel> mListHotel;
    private onItemClickListener mOnItemClickListener;

    public TopHotelAdapter(List<Hotel> listHotel, onItemClickListener onItemClickListener) {
        mListHotel = listHotel;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public TopHotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_top_hotel, parent, false);
        return new TopHotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopHotelViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListHotel.size();
    }

    class TopHotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView mCvItemHotel;
        private LinearLayout mLlItemHotel;
        private ImageView mImgHotel;
        private TextView mTvHotelName;
        private RatingBar mRatingHotel;
        private TextView mTvNumCommentHotel;
        private TextView mTvCostHotel;

        TopHotelViewHolder(final View itemView) {
            super(itemView);
            initViews();
            initListener();
        }

        private void initViews() {
            mCvItemHotel = itemView.findViewById(R.id.cardViewItemHotel);
            mLlItemHotel = itemView.findViewById(R.id.llItemHotel);
            mImgHotel = itemView.findViewById(R.id.imgTopHotel);
            mTvHotelName = itemView.findViewById(R.id.tvTopHotelName);
            mRatingHotel = itemView.findViewById(R.id.ratingHotel);
            mTvNumCommentHotel = itemView.findViewById(R.id.tvNumCommentHotel);
            mTvCostHotel = itemView.findViewById(R.id.tvCostHotel);
        }

        private void initListener() {
            mCvItemHotel.setOnClickListener(this);
        }

        private void onBindData() {
            Hotel hotel = mListHotel.get(getAdapterPosition());
            mImgHotel.setImageResource(hotel.getImage());
            mTvHotelName.setText(hotel.getName());
            mRatingHotel.setRating(hotel.getRating());
            mTvNumCommentHotel.setText((String.valueOf(hotel.getNumComment())));
            mTvCostHotel.setText(String.valueOf(hotel.getCost()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardViewItemHotel:
                    mOnItemClickListener.onHotelClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface onItemClickListener {
        void onHotelClick(int position);
    }
}
