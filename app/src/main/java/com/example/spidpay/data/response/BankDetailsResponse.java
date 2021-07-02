package com.example.spidpay.data.response;

import com.google.gson.annotations.SerializedName;

public class BankDetailsResponse {

    public String accountHolderName, branchName, ifscCode,accountNo;

    @SerializedName("bankName")
    public CompanyType companyType;


}
