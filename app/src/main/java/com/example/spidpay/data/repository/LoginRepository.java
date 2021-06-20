package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.ChangePasswordRequest;
import com.example.spidpay.data.request.LoginRequest;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.interfaces.ForgotPassInterface;
import com.example.spidpay.interfaces.LoginInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    Context context;
    LoginInterface loginInterface;
    ForgotPassInterface forgotPassInterface;

    public LoginRepository(Context context, LoginInterface loginInterface, ForgotPassInterface forgotPassInterface) {
        this.context = context;
        this.loginInterface = loginInterface;
        this.forgotPassInterface = forgotPassInterface;
    }

    public MutableLiveData<LoginResponse> getLoginResposne(LoginRequest loginRequest) {
        MutableLiveData<LoginResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER).create(RetrofitInterface.class);
        Call<LoginResponse> call = retrofitInterface.user_login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            loginInterface.onFailed(error);
                        } else {
                            loginInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        loginInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    loginInterface.onFailed("No Internet");
                } else {
                    loginInterface.onFailed(t.getMessage());
                }
            }
        });

        return responseMutableLiveData;
    }

    public MutableLiveData<CommonResponse> getResetPass(ChangePasswordRequest changePasswordRequest) {
        MutableLiveData<CommonResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER).create(RetrofitInterface.class);
        Call<CommonResponse> call = retrofitInterface.resetPass(changePasswordRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            loginInterface.onFailed(error);
                        } else {
                            loginInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        loginInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    forgotPassInterface.onForgotFailed("No Internet");
                } else {
                    forgotPassInterface.onForgotFailed(t.getMessage());
                }
            }
        });

        return responseMutableLiveData;
    }


}
