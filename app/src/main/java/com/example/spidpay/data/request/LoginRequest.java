package com.example.spidpay.data.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    public String password, userName;

    @SerializedName("geoLocationsMap")
    public LocationMap locationMap;
}
