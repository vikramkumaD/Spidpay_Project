package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.response.InterrestedforResponse;
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

    public MutableLiveData<List<InterrestedforResponse>> getStaticData(String cat, String role) {
        MutableLiveData<List<InterrestedforResponse>> listMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_API).create(RetrofitInterface.class);
        Call<List<InterrestedforResponse>> call = retrofitInterface.getstaticdata(cat, role);
        call.enqueue(new Callback<List<InterrestedforResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<InterrestedforResponse>> call, @NotNull Response<List<InterrestedforResponse>> response) {
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
            public void onFailure(@NotNull Call<List<InterrestedforResponse>> call, @NotNull Throwable t) {
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