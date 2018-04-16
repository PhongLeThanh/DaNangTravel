package com.example.phongle.danangtravel.activity.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.ListCommentViewHolder> {
    private List<User> mListUser;

    public ListCommentAdapter(List<User> listUser) {
        mListUser = listUser;
    }

    class ListCommentViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImgAvatar;
        private TextView mUsername;
        private RatingBar mRating;

        ListCommentViewHolder(final View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mUsername = itemView.findViewById(R.id.tvUsername);
            mRating = itemView.findViewById(R.id.ratingPlace);
        }

        private void onBindData() {
            User user = mListUser.get(getAdapterPosition());
            mImgAvatar.setImageResource(user.getAvatar());
            mUsername.setText(user.getUsername());
            mRating.setRating(5);
        }
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
        return mListUser.size();
    }

}
