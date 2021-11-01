package com.example.spidpay.data.request;

import com.example.spidpay.data.response.InterestedResponse;
import com.google.gson.annotations.SerializedName;


public class BankResponse {
    public String accountHolderName, branchName, ifscCode;
    public String accountNo;

    @SerializedName("bankName")
    public InterestedResponse bankName;


}
