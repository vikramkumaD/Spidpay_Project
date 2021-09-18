package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.AddMoneyRequest;
import com.example.spidpay.data.request.TransferMoneyRequest;
import com.example.spidpay.data.response.AddMoneyResponse;
import com.example.spidpay.data.response.TransferMoenyResponse;
import com.example.spidpay.interfaces.AddMoneyInterface;
import com.example.spidpay.interfaces.TradeWalletInterface;
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
    TradeWalletInterface tradeWalletInterface;

    public AddMoneyRepository(Context context, AddMoneyInterface addMoneyInterface) {
        this.context = context;
        this.addMoneyInterface = addMoneyInterface;
    }

    public AddMoneyRepository(Context context, TradeWalletInterface addMoneyInterface) {
        this.context = context;
        this.tradeWalletInterface = addMoneyInterface;
    }


    public MutableLiveData<AddMoneyResponse> getAddModenyResponse(AddMoneyRequest addMoneyRequest) {
        MutableLiveData<AddMoneyResponse> addMoneyResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
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


    public MutableLiveData<TransferMoenyResponse> getTradeToSpidWalletTransfer(TransferMoneyRequest transferMoneyRequest) {
        MutableLiveData<TransferMoenyResponse> moenyResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<TransferMoenyResponse> call = retrofitInterface.getTransferResponse(transferMoneyRequest);
        call.enqueue(new Callback<TransferMoenyResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransferMoenyResponse> call, @NonNull Response<TransferMoenyResponse> response) {
                if (response.isSuccessful()) {
                    moenyResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            tradeWalletInterface.onFailed(error);
                        } else {
                            tradeWalletInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        tradeWalletInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransferMoenyResponse> call, @NonNull Throwable t) {
                if (t instanceof NoInternetException) {
                    tradeWalletInterface.onFailed(Constant.NO_INTERNET);
                } else {
                    tradeWalletInterface.onFailed(t.getMessage());
                }
            }
        });
        return moenyResponseMutableLiveData;
    }

}
