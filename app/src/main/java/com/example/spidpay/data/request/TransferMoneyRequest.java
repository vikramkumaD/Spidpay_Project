package com.example.spidpay.data.request;

import com.google.gson.annotations.SerializedName;

public class TransferMoneyRequest {

    public int amount;
    public String domain,notes,transactionCategory,userId;

    @SerializedName("bankTransfer")
    public BankTransferRequest bankTransfer;

}
