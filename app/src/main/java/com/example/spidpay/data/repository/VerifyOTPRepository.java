package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.VerifyOTPReqest;
import com.example.spidpay.data.response.VerifyOTPResponse;
import com.example.spidpay.interfaces.VerifyOTPInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import org.jetbrains.annotations.NotNull;

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
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<VerifyOTPResponse> call = retrofitInterface.verifyOTP(loginRequest);
        call.enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(@NotNull Call<VerifyOTPResponse> call, @NotNull Response<VerifyOTPResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
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
            public void onFailure(@NotNull Call<VerifyOTPResponse> call, @NotNull Throwable t) {
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
