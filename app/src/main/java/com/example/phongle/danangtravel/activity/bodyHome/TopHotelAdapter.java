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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_place, parent, false);
        return new TopHotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopHotelViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListHotel == null ? 0 :mListHotel.size();
    }

    public interface onItemClickListener {
        void onHotelClick(int position);
    }

    class TopHotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            mLlItemHotel = itemView.findViewById(R.id.llItemTopPlace);
            mImgHotel = itemView.findViewById(R.id.imgTopPlace);
            mTvHotelName = itemView.findViewById(R.id.tvPlaceName);
            mRatingHotel = itemView.findViewById(R.id.ratingPlace);
            mTvNumCommentHotel = itemView.findViewById(R.id.tvNumComment);
            mTvCostHotel = itemView.findViewById(R.id.tvCostHotel);
        }

        private void initListener() {
            mLlItemHotel.setOnClickListener(this);
        }

        private void onBindData() {
            Hotel hotel = mListHotel.get(getAdapterPosition());
            if (hotel.getImages() != null && hotel.getImages().size() > 0 && hotel.getImages().get(0).getImageName() != null) {
                Glide.with(mImgHotel.getContext()).load(ReWriteUrl.reWriteUrl(hotel.getImages().get(0).getImageName()))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                mImgHotel.setImageResource(R.drawable.bg_default);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(mImgHotel);
            }else {
                mImgHotel.setImageResource(R.drawable.bg_default);
            }
            mTvHotelName.setText(hotel.getPlaceName());
            mRatingHotel.setRating(hotel.getRating());
            mTvNumCommentHotel.setText((String.valueOf(hotel.getNumcomment())));
            mTvCostHotel.setText(String.valueOf(hotel.getCost()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llItemTopPlace:
                    mOnItemClickListener.onHotelClick(getAdapterPosition());
            }
        }
    }
}
