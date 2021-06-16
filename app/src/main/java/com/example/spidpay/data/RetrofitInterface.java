package com.example.spidpay.data;

import com.example.spidpay.data.request.LoginRequest;
import com.example.spidpay.data.request.RegisterRequest;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.data.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @Headers({"Content-Type:application/json"})
    @POST("v1/login")
    Call<LoginResponse> user_login(@Body LoginRequest loginRequest);


    @Headers({"Content-Type:application/json"})
    @POST("user/v1/onBoarding")
    Call<RegisterResponse> user_onBoarding(@Body RegisterRequest loginRequest);

}
