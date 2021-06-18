package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.KYCResponse;

public interface KYCInterface extends CommonInterface {

    void onSuccess(LiveData<KYCResponse> kycResponseLiveData);
}
