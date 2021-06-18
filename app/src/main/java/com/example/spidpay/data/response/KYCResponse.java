package com.example.spidpay.data.response;

import com.google.gson.annotations.SerializedName;

public class KYCResponse {
    public String panNo, aadharNo, idNo;
    @SerializedName("idType")
    public IDType idType;
}
