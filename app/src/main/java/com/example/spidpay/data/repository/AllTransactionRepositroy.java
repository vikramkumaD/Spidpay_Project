package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.response.AllTransactionResponse;
import com.example.spidpay.interfaces.AllTransactionInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTransactionRepositroy {
    Context context;
    AllTransactionInterface allTransactionInterface;

    public AllTransactionRepositroy(Context context, AllTransactionInterface allTransactionInterface) {
        this.context = context;
        this.allTransactionInterface = allTransactionInterface;
    }

    public MutableLiveData<List<AllTransactionResponse>> getAllTransaction(String walletid, String pageno, String pagesize) {
        MutableLiveData<List<AllTransactionResponse>> listMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.TRANSACTION_API).create(RetrofitInterface.class);
        Call<List<AllTransactionResponse>> call = retrofitInterface.getAllTransaction(walletid, pageno, pagesize);
        call.enqueue(new Callback<List<AllTransactionResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<AllTransactionResponse>> call, @NotNull Response<List<AllTransactionResponse>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        listMutableLiveData.setValue(response.body());
                    } else {
                        allTransactionInterface.onFailed("No Record Found!");
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            allTransactionInterface.onFailed(error);
                        } else {
                            allTransactionInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        allTransactionInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<AllTransactionResponse>> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    allTransactionInterface.onFailed(Constant.NO_INTERNET);
                } else {
                    allTransactionInterface.onFailed(t.getMessage());
                }
            }
        });
        return listMutableLiveData;
    }
}
