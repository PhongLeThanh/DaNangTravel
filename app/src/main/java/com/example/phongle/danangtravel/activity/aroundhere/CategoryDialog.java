package com.example.phongle.danangtravel.activity.aroundhere;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.Category;

import java.util.List;

public class CategoryDialog extends DialogFragment {
    private RecyclerView mRecyclerCategory;
    private CategoryAdapter mAdapter;

    public CategoryDialog() {
        mAdapter = new CategoryAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Chọn danh mục");
        getDialog().setCancelable(true);
        View view = inflater.inflate(R.layout.dialog_choose_category, container, false);
        mRecyclerCategory = view.findViewById(R.id.recyclerCategory);
        mRecyclerCategory.setAdapter(mAdapter);
        mRecyclerCategory.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    public void setData(List<Category> category) {
        mAdapter.setCategory(category);
    }

    public void setCallback(CategoryAdapter.OnCategoryClickListener onCategoryClickListener) {
        mAdapter.setOnCategoryClickListener(onCategoryClickListener);
    }
}
