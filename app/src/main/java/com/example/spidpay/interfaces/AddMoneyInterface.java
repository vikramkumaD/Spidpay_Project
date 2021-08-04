package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.AddMoneyResponse;

public interface AddMoneyInterface extends CommonInterface{

    void onOnlineSuccess(LiveData<AddMoneyResponse> addMoneyResponseLiveData);
}
