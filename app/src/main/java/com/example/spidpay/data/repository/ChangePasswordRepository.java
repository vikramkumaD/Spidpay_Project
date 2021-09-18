package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.ChangePasswordRequest;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.interfaces.ChangePasswordInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordRepository {
    Context context;
    ChangePasswordInterface changePasswordInterface;

    public ChangePasswordRepository(Context context, ChangePasswordInterface changePasswordInterface) {
        this.context = context;
        this.changePasswordInterface = changePasswordInterface;
    }


    public MutableLiveData<CommonResponse> changePassword(ChangePasswordRequest changePasswordRequest) {
        MutableLiveData<CommonResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<CommonResponse> call = retrofitInterface.changePassword(changePasswordRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NotNull Call<CommonResponse> call, @NotNull Response<CommonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            changePasswordInterface.onFailed(error);
                        } else {
                            changePasswordInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        changePasswordInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    changePasswordInterface.onFailed("No Internet");
                } else {
                    changePasswordInterface.onFailed(t.getMessage());
                }
            }
        });

        return responseMutableLiveData;
    }
}
