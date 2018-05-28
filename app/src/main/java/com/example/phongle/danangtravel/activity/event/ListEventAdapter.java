package com.example.phongle.danangtravel.activity.event;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.utils.ReWriteUrl;
import com.example.phongle.danangtravel.models.Event;

import java.util.List;

public class ListEventAdapter extends RecyclerView.Adapter<ListEventAdapter.ListEventViewHolder> {
    private List<Event> mListEvent;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public ListEventAdapter(List<Event> listEvent, OnItemClickListener onItemClickListener, Context context) {
        mListEvent = listEvent;
        mOnItemClickListener = onItemClickListener;
        mContext = context;
    }

    @Override
    public ListEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_event, parent, false);
        return new ListEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListEventViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListEvent == null ? 0 : mListEvent.size();
    }

    public interface OnItemClickListener {
        void onEventClick(int position);
    }

    public class ListEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mllItemEvent;
        private ImageView mImgEvent;
        private TextView mTvEventName;
        private TextView mTvTimeStart;
        private TextView mTvTimeFinish;
        private TextView mTvDetail;
        private TextView mTvNumLike;

        public ListEventViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            initListener();
        }

        private void initViews(View itemView) {
            mllItemEvent = itemView.findViewById(R.id.llItemEvent);
            mImgEvent = itemView.findViewById(R.id.imgEvent);
            mTvEventName = itemView.findViewById(R.id.tvEventName);
            mTvTimeStart = itemView.findViewById(R.id.tvTimeStart);
            mTvTimeFinish = itemView.findViewById(R.id.tvTimeFinish);
            mTvDetail = itemView.findViewById(R.id.tvDetailEvent);
            mTvNumLike = itemView.findViewById(R.id.tvNumLike);
        }

        private void initListener() {
            mllItemEvent.setOnClickListener(this);
        }

        private void onBindData() {
            Event event = mListEvent.get(getAdapterPosition());
            if (event.getImage() != null && !event.getImage().isEmpty()) {
                Glide.with(mImgEvent.getContext()).load(ReWriteUrl.reWriteUrl(event.getImage()))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                mImgEvent.setImageResource(R.drawable.bg_default);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(mImgEvent);
            }else {
                mImgEvent.setImageResource(R.drawable.bg_default);
            }
            mTvEventName.setText(event.getEventName());
            mTvTimeStart.setText(event.getStart().substring(0, 10));
            mTvTimeFinish.setText(event.getFinish().substring(0, 10));
            mTvDetail.setText(event.getDetail());
            mTvNumLike.setText(event.getNumlike());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llItemEvent:
                    mOnItemClickListener.onEventClick(getAdapterPosition());
                    break;
            }
        }
    }
}
