package com.example.spidpay.data.request;

import com.google.gson.annotations.SerializedName;

public class PaytmTransferRequest {

    @SerializedName("customerName")
    public String CustomerName;

    @SerializedName("mobileNo")
    public String MobileNo;

    @SerializedName("serviceCharge")
    public String ServiceCharge;

    @SerializedName("validateBeneficiary")
    public Boolean ValidateBeneficiary;
}
