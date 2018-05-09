package com.example.phongle.danangtravel.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.api.UserResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtEmail;
    private Button mBtnSignup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_signup, container, false);
        initViews(v);
        initListener();
        return v;
    }

    private void initViews(View v) {
        mEdtUsername = v.findViewById(R.id.edtUsername);
        mEdtPassword = v.findViewById(R.id.edtPassword);
        mEdtEmail = v.findViewById(R.id.edtEmail);
        mBtnSignup = v.findViewById(R.id.btnRegister);
    }

    private void initListener() {
        mBtnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(mEdtUsername.getText()) && !TextUtils.isEmpty(mEdtPassword.getText()) && !TextUtils.isEmpty(mEdtEmail.getText())) {
            register(mEdtUsername.getText().toString(), mEdtPassword.getText().toString(), mEdtEmail.getText().toString());
        }
    }

    private void register(final String username, String password, String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("email", email);
            jsonObject.put("role", 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        MyRetrofit.getInstance().getService().signup(requestBody).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse != null && !userResponse.getData().isEmpty()) {
                    ((LoginActivity) getActivity()).replaceFragment(new LoginFragment(), true);
                    Toast.makeText(getContext(), "Đăng ký thành công !", Toast.LENGTH_LONG).show();
                    Log.d("xxx", "onResponse: thanh cong");
                } else {
                    Toast.makeText(getContext(), "Đăng ký không thành công !", Toast.LENGTH_LONG).show();
                    Log.d("xxx", "onResponse: khong thanh cong");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Đăng ký không thành công !", Toast.LENGTH_LONG).show();
                Log.d("xxx", "onResponse: post khong thanh cong");
            }
        });
    }
}
