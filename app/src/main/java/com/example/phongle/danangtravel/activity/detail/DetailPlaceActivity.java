package com.example.phongle.danangtravel.activity.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.models.User;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailPlaceActivity extends AppCompatActivity {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbarDetail;
    private HeaderDetailAdapter mHeaderDetailAdapter;
    private int mListImage[] = {R.drawable.bg_detail_place, R.drawable.bg_detail_place_1, R.drawable.bg_detail_place_2};
    private RecyclerView mRecyclerViewComment;
    private ListCommentAdapter mListCommentAdapter;
    private List<User> mListUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        initViews();
        initListener();
        initData();
        initAdapter();
        initCircleIndicator();
        mCollapsingToolbarLayout.setTitle("Dragon Bridge");
        setSupportActionBar(mToolbarDetail);
    }

    private void initViews() {
        mCollapsingToolbarLayout = findViewById(R.id.detailCollapsingToolbar);
        mViewPager = findViewById(R.id.viewPagerDetailPlace);
        mToolbarDetail = findViewById(R.id.toolbarDetailPlace);
        mRecyclerViewComment = findViewById(R.id.recyclerViewComment);
    }

    private void initListener() {

    }

    private void initAdapter() {
        // Set Adapter for view pager header detail
        mHeaderDetailAdapter = new HeaderDetailAdapter(this, mListImage);
        mViewPager.setAdapter(mHeaderDetailAdapter);
        // Set Adapter for recycler view Comment and rating
        mListCommentAdapter = new ListCommentAdapter(mListUser);
        mRecyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewComment.setAdapter(mListCommentAdapter);
    }

    private void initData() {
        mListUser = new ArrayList<>();
        mListUser.add(new User("Phong Lê Thanh", R.drawable.bg_avatar));
        mListUser.add(new User("Lê Anh Đức", R.drawable.bg_avatar));
        mListUser.add(new User("Cristiano Ronaldo", R.drawable.bg_avatar));
    }

    private void initCircleIndicator() {
        CircleIndicator circleIndicator = findViewById(R.id.circleIndicatorDetail);
        circleIndicator.setViewPager(mViewPager);
    }
}
