package com.example.phongle.danangtravel.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.headerHome.DistrictSpinnerAdapter;
import com.example.phongle.danangtravel.activity.headerHome.HeaderAdapter;

import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private Spinner mSpinnerDistrict;
    private HeaderAdapter mHeaderAdapter;
    private int mListImage[] = {R.drawable.bg_banner_app, R.drawable.bg_banner_app_2, R.drawable.bg_banner_app_3};
    private String[] mListDistrict = {"Hai Chau", "Hoa Vang", "Lien Chieu", "Thanh Khe", "Son Tra", "Cam Le"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initAdapter();
        initCircleIndicator();
        mCollapsingToolbarLayout.setTitle("Collapsing");
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(mToolbar);
    }

    private void initView() {
        mCollapsingToolbarLayout =findViewById(R.id.homeCollapsingToolbar);
        mViewPager = findViewById(R.id.viewPagerHeaderHome);
        mToolbar = findViewById(R.id.toolbarHome);
        mSpinnerDistrict = findViewById(R.id.spinnerDistrict);
    }

    private void initAdapter() {
        mHeaderAdapter = new HeaderAdapter(this, mListImage);
        mViewPager.setAdapter(mHeaderAdapter);
        mSpinnerDistrict.setAdapter(new DistrictSpinnerAdapter(this, mListDistrict));
    }

    private void initCircleIndicator() {
        CircleIndicator circleIndicator = findViewById(R.id.circleIndicator);
        circleIndicator.setViewPager(mViewPager);
    }
}
