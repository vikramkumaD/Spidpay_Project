package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.AddMoneyRequest;
import com.example.spidpay.data.response.AddMoneyResponse;
import com.example.spidpay.interfaces.AddMoneyInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMoneyRepository {
    Context context;
    AddMoneyInterface addMoneyInterface;

    public AddMoneyRepository(Context context, AddMoneyInterface addMoneyInterface) {
        this.context = context;
        this.addMoneyInterface = addMoneyInterface;
    }

/*
    public MutableLiveData<AddMoneyResponse> getAddModenyResponse(AddMoneyRequest addMoneyRequest) {
        MutableLiveData<AddMoneyResponse> addMoneyResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.WALLET_API).create(RetrofitInterface.class);
        Call<AddMoneyResponse> call = retrofitInterface.addMoney(addMoneyRequest);
        call.enqueue(new Callback<AddMoneyResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddMoneyResponse> call, @NotNull Response<AddMoneyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addMoneyResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            addMoneyInterface.onFailed(error);
                        } else {
                            addMoneyInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        addMoneyInterface.onFailed(e.toString());
                    }
                }
            }
            @Override
            public void onFailure(Call<AddMoneyResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    addMoneyInterface.onFailed(Constant.NO_INTERNET);
                } else {
                    addMoneyInterface.onFailed(t.getMessage());
                }
            }
        });
        return addMoneyResponseMutableLiveData;
    }
*/

}
