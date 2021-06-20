package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.WalletResponse;

import java.util.List;

public interface LandingInterface extends CommonInterface {

    void onSuccess(LiveData<List<WalletResponse>> listLiveData);
}
