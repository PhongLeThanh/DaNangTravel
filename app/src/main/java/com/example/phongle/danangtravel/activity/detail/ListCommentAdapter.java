package com.example.phongle.danangtravel.activity.detail;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.utils.ReWriteUrl;
import com.example.phongle.danangtravel.models.Comment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.ListCommentViewHolder> {
    private List<Comment> mListComment;

    public ListCommentAdapter(List<Comment> listComment) {
        mListComment = listComment;
    }

    @Override
    public ListCommentAdapter.ListCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ListCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListCommentAdapter.ListCommentViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return mListComment ==null ? 0 :mListComment.size();
    }

    class ListCommentViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImgAvatar;
        private TextView mTvUsername;
        private RatingBar mEvaluate;
        private TextView mTvContent;

        ListCommentViewHolder(final View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvUsername = itemView.findViewById(R.id.tvUsername);
            mEvaluate = itemView.findViewById(R.id.evaluatePlace);
            mTvContent = itemView.findViewById(R.id.tvContentComment);
        }

        private void onBindData() {
            Comment comment = mListComment.get(getAdapterPosition());
            if (comment.getUser().getAvatar() != null) {
                Glide.with(mImgAvatar.getContext()).load(ReWriteUrl.reWriteUrl(comment.getUser().getAvatar()))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                mImgAvatar.setImageResource(R.drawable.bg_default_avatar);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(mImgAvatar);
            }else {
                mImgAvatar.setImageResource(R.drawable.bg_default_avatar);
            }
            mTvUsername.setText(comment.getUser().getUsername());
            mEvaluate.setRating(comment.getEvaluate());
            mTvContent.setText(comment.getContent());
        }
    }

}
