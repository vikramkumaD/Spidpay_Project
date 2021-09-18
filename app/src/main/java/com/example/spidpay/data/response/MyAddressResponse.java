package com.example.spidpay.data.response;

import com.google.gson.annotations.SerializedName;

public class MyAddressResponse {
    @SerializedName("address1")
    public String addressline;
    @SerializedName("address2")
    public String landmark;
    @SerializedName("address4")
    public String district;
    public String country = "India";
    @SerializedName("pinCode")
    public String pinCode;
    @SerializedName("state")
    public String state;
    @SerializedName("address3")
    public String city;
}
