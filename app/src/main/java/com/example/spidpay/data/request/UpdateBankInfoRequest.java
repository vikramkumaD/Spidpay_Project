package com.example.spidpay.data.request;

import com.google.gson.annotations.SerializedName;

public class UpdateBankInfoRequest {

    public String userId;

    @SerializedName("bankInfo")
    public BanKRequest banKRequest;

    public static class BanKRequest {
        public String accountHolderName, branchName, ifscCode, bankName,accountNo;
    }

}
