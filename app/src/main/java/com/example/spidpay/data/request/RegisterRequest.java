package com.example.spidpay.data.request;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    public String sendOTP,webPortal;
    @SerializedName("userInfo")
    public Register_UserInfo register_userInfo;
}
