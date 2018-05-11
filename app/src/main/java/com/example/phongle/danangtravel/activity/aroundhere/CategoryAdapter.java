package com.example.phongle.danangtravel.activity.aroundhere;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> mCategory;
    private OnCategoryClickListener mOnCategoryClickListener;

    public CategoryAdapter() {
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.mTvCategory.setText(mCategory.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return mCategory == null ? 0 : mCategory.size();
    }

    public void setCategory(List<Category> categories) {
        if (mCategory == null) {
            mCategory = new ArrayList<>();
        }
        if (categories != null && categories.size() > 0) {
            mCategory.clear();
            mCategory = categories;
            notifyDataSetChanged();
        }
    }

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        mOnCategoryClickListener = onCategoryClickListener;
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            mTvCategory = itemView.findViewById(R.id.tvCategory);
            mTvCategory.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnCategoryClickListener != null) {
                mOnCategoryClickListener.onCategoryClick(mCategory.get(getAdapterPosition()));
            }
        }
    }
}
