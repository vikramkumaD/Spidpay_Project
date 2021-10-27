package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.response.InterestedResponse;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaticRepository {
    public Context context;
    public StaticInterface staticInterface;

    public StaticRepository(Context context, StaticInterface staticInterface) {
        this.context = context;
        this.staticInterface = staticInterface;
    }

    public MutableLiveData<List<InterestedResponse>> getStaticData(String cat, String role) {
        MutableLiveData<List<InterestedResponse>> listMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<List<InterestedResponse>> call = retrofitInterface.getStaticData(cat, role);
        call.enqueue(new Callback<List<InterestedResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<InterestedResponse>> call, @NotNull Response<List<InterestedResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            staticInterface.onStaticFailed(error);
                        } else {
                            staticInterface.onStaticFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        staticInterface.onStaticFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<InterestedResponse>> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    staticInterface.onStaticFailed("No Internet");
                } else {
                    staticInterface.onStaticFailed(t.getMessage());
                }
            }
        });
        return listMutableLiveData;

    }
}
