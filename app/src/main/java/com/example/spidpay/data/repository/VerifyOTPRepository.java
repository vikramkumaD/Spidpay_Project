package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.LoginRequest;
import com.example.spidpay.data.request.VerifyOTPReqest;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.data.response.VerifyOTPResponse;
import com.example.spidpay.interfaces.VerifyOTPInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPRepository {

    Context context;
    VerifyOTPInterface verifyOTPInterface;

    public VerifyOTPRepository(Context context, VerifyOTPInterface verifyOTPInterface) {
        this.context = context;
        this.verifyOTPInterface = verifyOTPInterface;
    }

    public MutableLiveData<VerifyOTPResponse> getVerifyOTP(VerifyOTPReqest loginRequest) {
        MutableLiveData<VerifyOTPResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<VerifyOTPResponse> call = retrofitInterface.verifyOTP(loginRequest);
        call.enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody, context);
                            verifyOTPInterface.onFailed(error);
                        } else {
                            verifyOTPInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        verifyOTPInterface.onFailed(e.toString());
                    }
                }
            }
            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    verifyOTPInterface.onFailed("No Internet");
                } else {
                    verifyOTPInterface.onFailed(t.getMessage());
                }
            }
        });

        return responseMutableLiveData;
    }
}
