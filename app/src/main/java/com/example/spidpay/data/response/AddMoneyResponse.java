package com.example.spidpay.data.response;

import com.google.gson.annotations.SerializedName;

public class AddMoneyResponse {

    public String amount, creationTime, transactionCategory, transactionStatus, txnId, updateTime, walletId, walletType;

    @SerializedName("payuTransactionDetails")
    public OnlineResponse onlineResponse;
}
