package com.example.spidpay.data.request;

import com.google.gson.annotations.SerializedName;

public class TransferMoneyRequest {

    public String amount;
    public String domain,notes,transactionCategory,userId;

    @SerializedName("bankTransfer")
    public BankTransferRequest bankTransfer;

    @SerializedName("paytmWalletTransfer")
    public PaytmTransferRequest paytmTransferRequest;

}
