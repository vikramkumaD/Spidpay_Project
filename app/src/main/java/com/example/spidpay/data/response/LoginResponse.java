package com.example.spidpay.data.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    public String accessToken, refreshToken, status, firstTimeLogin, userScope, userType;

    @SerializedName("userData")
    public Login_UserData loginUserData;
}
