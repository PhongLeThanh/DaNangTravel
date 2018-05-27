package com.example.phongle.danangtravel.activity.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.HomeActivity;
import com.example.phongle.danangtravel.activity.utils.FileUtil;
import com.example.phongle.danangtravel.activity.utils.ReWriteUrl;
import com.example.phongle.danangtravel.api.ImageUpLoadResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.UpdateUserResponse;
import com.example.phongle.danangtravel.api.UserResponse;
import com.example.phongle.danangtravel.models.User;
import com.example.phongle.danangtravel.models.shareds.SharedPrefeencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMG = 1000;
    private ImageView mImgBack;
    private ImageView mImgEdit;
    private ImageView mImgLogout;
    private ImageView mImgAvatar;
    private TextView mTvUsername;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mImgBack = findViewById(R.id.imgBack);
        mImgEdit = findViewById(R.id.imgEdit);
        mImgLogout = findViewById(R.id.imgLogout);
        mImgAvatar = findViewById(R.id.imgAvatar);
        mUser = SharedPrefeencesUtils.getUser();
        if (mUser.getAvatar() != null) {
            Glide.with(mImgAvatar.getContext()).load(ReWriteUrl.reWriteUrl(mUser.getAvatar()))
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
        }
        mTvUsername = findViewById(R.id.tvUsername);
        mTvUsername.setText(mUser.getUsername());
        mImgBack.setOnClickListener(this);
        mImgEdit.setOnClickListener(this);
        mImgLogout.setOnClickListener(this);
        mImgAvatar.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.imgEdit:
                replaceFragment(new ProfileFragment(), false);
                break;
            case R.id.imgLogout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
                alertDialogBuilder.setTitle(getResources().getString(R.string.alert_confirm_logout_title));
                alertDialogBuilder
                        .setMessage(getResources().getString(R.string.alert_confirm_logout))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPrefeencesUtils.clear();
                                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.imgAvatar:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            upload(FileUtil.getRealPathFromURI(this, imageUri));
        } else {

        }
    }

    private void upload(String path) {
        File file = FileUtil.resizeImageFile(path);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        MyRetrofit.getInstance().getService().uploadImage(photo).enqueue(new Callback<ImageUpLoadResponse>() {
            @Override
            public void onResponse(Call<ImageUpLoadResponse> call, Response<ImageUpLoadResponse> response) {
                ImageUpLoadResponse imageUpLoadResponse = response.body();
                if (imageUpLoadResponse != null && imageUpLoadResponse.getData() != null) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("avatar", imageUpLoadResponse.getData().getImageUrl());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
                    MyRetrofit.getInstance().getService().updateUser(mUser.getId(), requestBody).enqueue(new Callback<UpdateUserResponse>() {
                        @Override
                        public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                            int result = response.body().getResult();
                            if (result == 1) {
                                getUserById();
                                Toast.makeText(getApplicationContext(), "Cập nhật avatar thành công !", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Cập nhật avatar không thành công !", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateUserResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ImageUpLoadResponse> call, Throwable t) {

            }
        });
    }

    private void getUserById() {
        MyRetrofit.getInstance().getService().getUser(mUser.getId()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse != null && userResponse.getData() != null) {
                    User user = userResponse.getData();
                    SharedPrefeencesUtils.putUser(user);
                    mUser = SharedPrefeencesUtils.getUser();
                    if (mUser.getAvatar() != null) {
                        Glide.with(mImgAvatar.getContext()).load(ReWriteUrl.reWriteUrl(mUser.getAvatar()))
                                .into(mImgAvatar);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
}
