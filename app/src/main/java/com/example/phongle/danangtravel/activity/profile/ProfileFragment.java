package com.example.phongle.danangtravel.activity.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.UpdateUserResponse;
import com.example.phongle.danangtravel.api.UpdateProfileResponse;
import com.example.phongle.danangtravel.api.UserResponse;
import com.example.phongle.danangtravel.models.User;
import com.example.phongle.danangtravel.models.shareds.SharedPrefeencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtPassword;
    private EditText mEdtEmail;
    private EditText mEdtFullname;
    private EditText mEdtAge;
    private EditText mEdtAddress;
    private EditText mEdtPhone;
    private Button mBtnUpdatePass;
    private Button mBtnUpdate;
    private User mUser = SharedPrefeencesUtils.getUser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_your_profile, container, false);
        initViews(v);
        initListener();
        updateView();
        return v;
    }

    private void initViews(View v) {
        mEdtPassword = v.findViewById(R.id.edtPassword);
        mEdtEmail = v.findViewById(R.id.edtEmail);
        mEdtFullname = v.findViewById(R.id.edtFullname);
        mEdtAge = v.findViewById(R.id.edtAge);
        mEdtAddress = v.findViewById(R.id.edtAddress);
        mEdtPhone = v.findViewById(R.id.edtPhone);
        mBtnUpdate = v.findViewById(R.id.btnUpdate);
        mBtnUpdatePass = v.findViewById(R.id.btnUpdatePass);

    }

    private void initListener() {

        mBtnUpdate.setOnClickListener(this);
        mBtnUpdatePass.setOnClickListener(this);
    }

    private void updateView() {
        mEdtEmail.setText(mUser.getEmail());
        if (mUser.getProfile() != null) {
            if (mUser.getProfile().getFullname() != null) {
                mEdtFullname.setText(mUser.getProfile().getFullname() + "");
            }
            if (mUser.getProfile().getAge() != null) {
                mEdtAge.setText(mUser.getProfile().getAge() + "");
            }
            if (mUser.getProfile().getAddress() != null) {
                mEdtAddress.setText(mUser.getProfile().getAddress() + "");
            }
            if (mUser.getProfile().getPhone() != null) {
                mEdtPhone.setText(mUser.getProfile().getPhone() + "");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                update(mEdtFullname.getText().toString(), mEdtAge.getText().toString(), mEdtAddress.getText().toString(), mEdtPhone.getText().toString());
                break;
            case R.id.btnUpdatePass:
                if(!TextUtils.isEmpty(mEdtPassword.getText())){
                    updatePass(mEdtPassword.getText().toString());
                }
                break;
        }

    }

    private void updatePass(String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBodyUser = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        MyRetrofit.getInstance().getService().updateUser(mUser.getId(), requestBodyUser).enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                int result = response.body().getResult();
                if (result == 1) {
                    getUserById();
                    Toast.makeText(getContext(), "Cập nhật mật khẩu thành công !", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "Cập nhật mật khẩu không thành công !", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Cập nhật mật khẩu không thành công !", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void update(String fullname, String age, String address, String phone) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fullname", fullname);
            jsonObject.put("age", age);
            jsonObject.put("address", address);
            jsonObject.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        MyRetrofit.getInstance().getService().updateProfile(mUser.getId(), requestBody).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                int profileResponse = response.body().getResult().get(0);
                if (profileResponse == 1) {
                    getUserById();
                    Toast.makeText(getContext(), "Cập nhật thành công !", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Cập nhật không thành công !", Toast.LENGTH_LONG).show();
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
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
}
