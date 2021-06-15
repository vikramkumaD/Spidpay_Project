package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.LoginRequest;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.interfaces.CommonInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    Context context;
    CommonInterface commonInterface;

    public LoginRepository(Context context, CommonInterface commonInterface) {
        this.context = context;
        this.commonInterface = commonInterface;
    }

    public MutableLiveData<LoginResponse> getLoginResposne(LoginRequest loginRequest) {
        MutableLiveData<LoginResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<LoginResponse> call = retrofitInterface.user_login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                } else {
                    commonInterface.onFailed("Server error!");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                commonInterface.onFailed("Server error!");
            }
        });

        return responseMutableLiveData;
    }
}
