package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.RegisterResponse;

public interface RegisterInterface extends CommonInterface {
    void onSuccess(LiveData<RegisterResponse> responseLiveData);
}
