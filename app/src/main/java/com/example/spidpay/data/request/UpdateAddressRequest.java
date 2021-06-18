package com.example.spidpay.data.request;

import com.example.spidpay.data.response.MyAddressResponse;
import com.google.gson.annotations.SerializedName;

public class UpdateAddressRequest {
    public String userId;

    @SerializedName("address")
    public MyAddressResponse myAddressResponse;
}
