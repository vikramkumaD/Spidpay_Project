package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.AllTransactionResponse;

import java.util.List;

public interface AllTransactionInterface extends CommonInterface{

    void onSuccess(LiveData<List<AllTransactionResponse>> allTransactionResponses);

}
