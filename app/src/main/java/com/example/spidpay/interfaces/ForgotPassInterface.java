package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.BooleanResponse;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.VerifyOTPResponse;

public interface ForgotPassInterface {
    void onForgotVerifySuccess(LiveData<BooleanResponse> commonResponseLiveData);

    void onForgotResetSuccess(LiveData<CommonResponse> commonResponseLiveData);

    void onVerifyOTPSuccess(LiveData<VerifyOTPResponse> commonResponseLiveData);

    void onForgotFailed(String msg);

    void onForgotStart();
}
