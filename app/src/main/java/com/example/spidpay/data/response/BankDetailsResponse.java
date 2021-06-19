package com.example.spidpay.data.response;

import androidx.databinding.InverseMethod;
import com.google.gson.annotations.SerializedName;

public class BankDetailsResponse {

    public String accountHolderName, branchName, ifscCode;
    public int accountNo;

    @SerializedName("bankName")
    public CompanyType companyType;


}
