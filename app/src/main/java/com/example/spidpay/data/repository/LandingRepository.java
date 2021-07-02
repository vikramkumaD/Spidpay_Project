package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.response.WalletResponse;
import com.example.spidpay.interfaces.LandingInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingRepository {
    final Context context;
    final LandingInterface landingInterface;

    public LandingRepository(Context context, LandingInterface landingInterface) {
        this.context = context;
        this.landingInterface = landingInterface;
    }

    public MutableLiveData<List<WalletResponse>> getWallet(String userid) {
        MutableLiveData<List<WalletResponse>> listMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.WALLET_API).create(RetrofitInterface.class);
        Call<List<WalletResponse>> call = retrofitInterface.getWalletResponse(userid);
        call.enqueue(new Callback<List<WalletResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<WalletResponse>> call, @NotNull Response<List<WalletResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            landingInterface.onFailed(error);
                        } else {
                            landingInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        landingInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<WalletResponse>> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    landingInterface.onFailed("No Internet");
                } else {
                    landingInterface.onFailed(t.getMessage());
                }
            }
        });
        return listMutableLiveData;
    }
}
