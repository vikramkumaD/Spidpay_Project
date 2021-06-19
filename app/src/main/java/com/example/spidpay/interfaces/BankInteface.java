package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.BankDetailsResponse;
import com.example.spidpay.data.response.UpdateResponse;

public interface BankInteface extends CommonInterface{


    void onSuccess(LiveData<BankDetailsResponse> bankDetailsResponseLiveData);


    void onUpdateSuccess(LiveData<UpdateResponse> updateResponseLiveData);
}
