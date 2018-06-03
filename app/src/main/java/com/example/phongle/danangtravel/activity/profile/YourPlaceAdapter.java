package com.example.phongle.danangtravel.activity.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.Place;
import com.example.phongle.danangtravel.utils.ReWriteUrl;

import java.util.List;

public class YourPlaceAdapter extends RecyclerView.Adapter<YourPlaceAdapter.YourPlaceViewHolder> {
    private Context mContext;
    private List<Place> mListPlace;
    private OnItemClickListener mOnItemClickListener;

    public YourPlaceAdapter(Context context, List<Place> listPlace, OnItemClickListener onItemClickListener) {
        mContext = context;
        mListPlace = listPlace;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public YourPlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_your_place, parent, false);
        return new YourPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YourPlaceViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListPlace == null ? 0 : mListPlace.size();
    }

    public interface OnItemClickListener {
        void onPlaceClick(int position);

        void onPlaceLongClick(int position);
    }

    public class YourPlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private LinearLayout mLlItemListPlace;
        private ImageView mImgPlace;
        private TextView mTvPlaceName;
        private TextView mTvDescriptionPlace;
        private RatingBar mRatingPlace;
        private TextView mTvNumCommentPlace;
        private PopupMenu popupMenu;

        public YourPlaceViewHolder(View itemView) {
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
            popupMenu = new PopupMenu(mContext, itemView);
            popupMenu.getMenuInflater().inflate(R.menu.menu_delete, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.itemDelete:
                            mOnItemClickListener.onPlaceLongClick(getAdapterPosition());
                    }
                    return false;
                }
            });
        }

        private void initListener() {
            mLlItemListPlace.setOnClickListener(this);
            mLlItemListPlace.setOnLongClickListener(this);
        }

        @SuppressLint("DefaultLocale")
        private void onBindData() {
            Place place = mListPlace.get(getAdapterPosition());
            if (place.getImages() != null && place.getImages().get(0).getImageName() != null) {
                Glide.with(mImgPlace.getContext()).load(ReWriteUrl.reWriteUrl(place.getImages().get(0).getImageName()))
                        .apply(new RequestOptions().placeholder(R.drawable.bg_default_avatar))
                        .into(mImgPlace);
            }
            mTvPlaceName.setText(place.getPlaceName());
            mTvDescriptionPlace.setText(place.getDescription());
            mRatingPlace.setRating(place.getRating());
            mTvNumCommentPlace.setText((String.valueOf(place.getNumcomment())));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llItemPlace:
                    mOnItemClickListener.onPlaceClick(getAdapterPosition());
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.llItemPlace:
                    popupMenu.show();
                    break;
            }
            return false;
        }
    }
}
