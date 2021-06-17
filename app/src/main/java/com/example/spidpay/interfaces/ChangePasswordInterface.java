package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.CommonResponse;

public interface ChangePasswordInterface extends CommonInterface{

    void onSuccess(LiveData<CommonResponse> commonResponseLiveData);
}
