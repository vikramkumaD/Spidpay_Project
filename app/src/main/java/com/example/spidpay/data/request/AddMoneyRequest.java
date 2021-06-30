package com.example.spidpay.data.request;

import com.google.gson.annotations.SerializedName;

public class AddMoneyRequest {

    public String amount, domain, notes, userId, walletType, transactionCategory;

    @SerializedName("cashTransaction")
    public CashTransaction cashTransaction;

    @SerializedName("payuTransaction")
    public OnlineTransaction onlineTransaction;

    @SerializedName("walletTransfer")
    public WalletTransfer walletTransfer;
}
