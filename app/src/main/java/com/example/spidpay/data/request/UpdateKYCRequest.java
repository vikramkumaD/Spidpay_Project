package com.example.spidpay.data.request;

import com.example.spidpay.data.response.IDType;
import com.example.spidpay.data.response.KYCResponse;
import com.google.gson.annotations.SerializedName;

public class UpdateKYCRequest {
    public String userId;

    @SerializedName("kyc")
    public KYCRequest kycResponse;

    public static class KYCRequest {
        public String panNo, aadharNo, idNo, idType;
    }
}
