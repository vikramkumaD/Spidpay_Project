package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.RegisterRequest;
import com.example.spidpay.data.response.InterrestedforResponse;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.data.response.RegisterResponse;
import com.example.spidpay.interfaces.RegisterInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    Context context;
    RegisterInterface registerInterface;

    public RegisterRepository(Context context, RegisterInterface registerInterface) {
        this.context = context;
        this.registerInterface = registerInterface;
    }

    public MutableLiveData<RegisterResponse> getRegisterResponse(RegisterRequest registerRequest) {
        MutableLiveData<RegisterResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<RegisterResponse> call = retrofitInterface.user_onBoarding(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseMutableLiveData.postValue(response.body());

                } else {
                    registerInterface.onFailed("Server error!");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    registerInterface.onFailed("No Internet");
                } else {
                    registerInterface.onFailed(t.getMessage());
                }
            }
        });
        return responseMutableLiveData;
    }

    public MutableLiveData<List<InterrestedforResponse>> getInterrestedfor() {
        MutableLiveData<List<InterrestedforResponse>> listMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<List<InterrestedforResponse>> call = retrofitInterface.getInterrestedfor(Constant.USER, Constant.ROLE_INTERRESTEDFOR);
        call.enqueue(new Callback<List<InterrestedforResponse>>() {
            @Override
            public void onResponse(Call<List<InterrestedforResponse>> call, Response<List<InterrestedforResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listMutableLiveData.postValue(response.body());
                } else {
                    registerInterface.onFailed("Server error!");
                }
            }

            @Override
            public void onFailure(Call<List<InterrestedforResponse>> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    registerInterface.onFailed("No Internet");
                } else {
                    registerInterface.onFailed(t.getMessage());
                }
            }
        });
        return listMutableLiveData;

    }
}
