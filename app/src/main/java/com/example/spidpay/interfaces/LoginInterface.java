package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.LoginResponse;

public interface LoginInterface extends CommonInterface {

    void onSuccess(LiveData<LoginResponse> responseLiveData);
}
