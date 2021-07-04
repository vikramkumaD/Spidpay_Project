package com.example.spidpay.data.request;

import com.google.gson.annotations.SerializedName;

public class AddMoneyRequest {

    public String amount, domain, notes, userId, walletType, transactionCategory;

    @SerializedName("cashTransaction")
    public CashTransactionRequest cashTransactionRequest;

    @SerializedName("payuTransaction")
    public OnlineTransactionRequest onlineTransactionRequest;

    @SerializedName("walletTransfer")
    public WalletTransferRequest walletTransferRequest;
}
