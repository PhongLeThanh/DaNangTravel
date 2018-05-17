package com.example.phongle.danangtravel.activity.bodyHome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.utils.ReWriteUrl;
import com.example.phongle.danangtravel.models.Event;

import java.util.List;

public class HotEventAdapter extends RecyclerView.Adapter<HotEventAdapter.HotEventViewHolder> {
    private List<Event> mListEvent;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;


    public HotEventAdapter(List<Event> listEvent, Context context, OnItemClickListener onItemClickListener) {
        mListEvent = listEvent;
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public HotEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_hot_event, parent, false);
        return new HotEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotEventViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListEvent == null ? 0 : mListEvent.size();
    }

    public interface OnItemClickListener {
        void onEventClick(int position);
    }

    class HotEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mllItemEvent;
        private ImageView mImgEvent;
        private TextView mTvEventName;
        private TextView mTvTimeStart;
        private TextView mTvTimeFinish;
        private TextView mTvNumLike;

        public HotEventViewHolder(View itemView) {
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
            mTvNumLike = itemView.findViewById(R.id.tvNumLike);
        }

        private void initListener() {
            mllItemEvent.setOnClickListener(this);
        }

        private void onBindData() {
            Event event = mListEvent.get(getAdapterPosition());
            if (event.getImage() != null && !event.getImage().isEmpty()) {
                Glide.with(mImgEvent.getContext()).load(ReWriteUrl.reWriteUrl(event.getImage()))
                        .into(mImgEvent);
            }
            mTvEventName.setText(event.getEventName());
            mTvTimeStart.setText(event.getStart().substring(0, 10));
            mTvTimeFinish.setText(event.getFinish().substring(0, 10));
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
