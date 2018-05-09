package com.example.phongle.danangtravel.activity.login;

import android.content.Intent;
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
import com.example.phongle.danangtravel.activity.HomeActivity;
import com.example.phongle.danangtravel.api.LoginResponse;
import com.example.phongle.danangtravel.api.MyRetrofit;
import com.example.phongle.danangtravel.models.shareds.SharedPrefeencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private Button mBtnSignup;

    public static Fragment getInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        initListener();
        return view;
    }

    private void initViews(View view) {
        mEdtUsername = view.findViewById(R.id.edtUsername);
        mEdtPassword = view.findViewById(R.id.edtPassword);
        mBtnLogin = view.findViewById(R.id.btnLogin);
        mBtnSignup = view.findViewById(R.id.btnSignUp);
    }

    private void initListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            if (!TextUtils.isEmpty(mEdtUsername.getText()) && !TextUtils.isEmpty(mEdtPassword.getText())) {
                login(mEdtUsername.getText().toString(), mEdtPassword.getText().toString());
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập username và password !", Toast.LENGTH_LONG).show();
            }
        }
        if (v.getId() == R.id.btnSignUp) {
            ((LoginActivity) getActivity()).replaceFragment(new SignupFragment(), true);
        }
    }

    public void login(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        MyRetrofit.getInstance().getAuthService().login(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("xxx", "onSubscribe: subscribe");
                    }

                    @Override
                    public void onNext(LoginResponse value) {
                        if (!value.getData().isEmpty()) {
                            SharedPrefeencesUtils.putDocument(value.getData().getToken());
                            SharedPrefeencesUtils.putUser(value.getData());
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(getContext(), "Đăng nhập thành công !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Đăng nhập thất bại !", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("xxx", "complete");
                    }
                });
    }
}
