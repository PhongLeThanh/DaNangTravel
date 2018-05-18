package com.example.phongle.danangtravel.activity.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.phongle.danangtravel.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mImgBack;
    private ImageView mImgEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mImgBack = findViewById(R.id.imgBack);
        mImgEdit = findViewById(R.id.imgEdit);
        mImgBack.setOnClickListener(this);
        mImgEdit.setOnClickListener(this);
        replaceFragment(YourPlaceFragment.getInstance(), false);
    }

    protected void replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frContainer, fragment);
        if (addBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        }
        if (v.getId() == R.id.imgEdit){
            replaceFragment(new ProfileFragment(), false);
        }
    }
}
