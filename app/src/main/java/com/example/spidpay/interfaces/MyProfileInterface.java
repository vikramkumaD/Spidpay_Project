package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.MyAddressResponse;
import com.example.spidpay.data.response.MyProfileResponse;
import com.example.spidpay.data.response.UpdateResponse;

public interface MyProfileInterface extends CommonInterface {
    void onProfileSuccess(LiveData<MyProfileResponse> myProfileResponseLiveData);

    void onAddressSuccess(LiveData<MyAddressResponse> myProfileResponseLiveData);

    void onUpdateAddress(LiveData<UpdateResponse> commonResponseLiveData);

}
