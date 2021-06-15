package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.LoginResponse;

public interface CommonInterface {

    void onFailed(String msg);
    void onSuccess(LiveData<LoginResponse> responseLiveData);
}
