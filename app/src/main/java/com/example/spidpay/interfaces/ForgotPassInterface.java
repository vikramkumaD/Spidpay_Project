package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.CommonResponse;

public interface ForgotPassInterface {
    void onForgotSuccess(LiveData<CommonResponse> commonResponseLiveData);

    void onForgotFailed(String msg);

    void onForgotStart();
}
