package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.TransferMoenyResponse;

public interface TradeWalletInterface extends CommonInterface {

    public void onTransferSuccess(LiveData<TransferMoenyResponse> liveData);

}
