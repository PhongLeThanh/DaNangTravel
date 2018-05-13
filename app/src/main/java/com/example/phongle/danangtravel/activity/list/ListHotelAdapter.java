package com.example.phongle.danangtravel.activity.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.utils.ReWriteUrl;
import com.example.phongle.danangtravel.models.Hotel;

import java.util.List;

public class ListHotelAdapter extends RecyclerView.Adapter<ListHotelAdapter.ListHotelViewHolder> {
    private List<Hotel> mListHotel;
    private onItemClickListener mOnItemClickListener;

    public ListHotelAdapter(List<Hotel> listHotel, onItemClickListener onItemClickListener) {
        mListHotel = listHotel;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ListHotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_place, parent, false);
        return new ListHotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHotelViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListHotel == null ? 0 : mListHotel.size();
    }

    public interface onItemClickListener {
        void onPlaceClick(int position);
    }

    class ListHotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mLlItemListHotel;
        private ImageView mImgHotel;
        private TextView mTvHotelName;
        private TextView mTvDescriptonPlace;
        private RatingBar mRatingHotel;
        private TextView mTvNumCommentHotel;
        private TextView mTvCostHotel;

        ListHotelViewHolder(final View itemView) {
            super(itemView);
            initViews();
            initListener();
        }

        private void initViews() {
            mLlItemListHotel = itemView.findViewById(R.id.llItemPlace);
            mImgHotel = itemView.findViewById(R.id.imgPlace);
            mTvHotelName = itemView.findViewById(R.id.tvPlaceName);
            mTvDescriptonPlace = itemView.findViewById(R.id.tvDescriptionPlace);
            mRatingHotel = itemView.findViewById(R.id.ratingPlace);
            mTvNumCommentHotel = itemView.findViewById(R.id.tvNumComment);
            mTvCostHotel = itemView.findViewById(R.id.tvCostHotel);
        }

        private void initListener() {
            mLlItemListHotel.setOnClickListener(this);
        }

        private void onBindData() {
            Hotel hotel = mListHotel.get(getAdapterPosition());
            if (hotel.getImages() != null && hotel.getImages().size() > 0 && hotel.getImages().get(0).getImageName() != null) {
                Glide.with(mImgHotel.getContext()).load(ReWriteUrl.reWriteUrl(hotel.getImages().get(0).getImageName()))
                        .into(mImgHotel);
            }
            mTvHotelName.setText(hotel.getPlaceName());
            mTvDescriptonPlace.setText(hotel.getDescription());
            mRatingHotel.setRating(hotel.getRating());
            mTvNumCommentHotel.setText((String.valueOf(hotel.getNumcomment())));
            mTvCostHotel.setText(String.valueOf(hotel.getCost()));
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
