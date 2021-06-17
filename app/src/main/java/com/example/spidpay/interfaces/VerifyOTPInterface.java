package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.VerifyOTPResponse;

public interface VerifyOTPInterface extends CommonInterface{

    void onSuccess(LiveData<VerifyOTPResponse> verifyOTPResponseLiveData);
}
