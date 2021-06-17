package com.example.spidpay.data;

import com.example.spidpay.data.request.ChangePasswordRequest;
import com.example.spidpay.data.request.LoginRequest;
import com.example.spidpay.data.request.RegisterRequest;
import com.example.spidpay.data.request.VerifyOTPReqest;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.InterrestedforResponse;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.data.response.RegisterResponse;
import com.example.spidpay.data.response.VerifyOTPResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @Headers({"Content-Type:application/json"})
    @POST("v1/login")
    Call<LoginResponse> user_login(@Body LoginRequest loginRequest);

    @Headers({"Content-Type:application/json"})
    @POST("user/v1/onBoarding")
    Call<RegisterResponse> user_onBoarding(@Body RegisterRequest loginRequest);

    @GET("static-data/v1/{category}")
    Call<List<InterrestedforResponse>> getInterrestedfor(@Path("category") String user, @Query("role") String role);

    @GET("change-password/v1")
    Call<CommonResponse> changepassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST("reset-password/v1")
    Call<CommonResponse> resetPass(@Body ChangePasswordRequest changePasswordRequest);

    @POST("otp/v1/verify-mobile-otp")
    Call<VerifyOTPResponse> verifyOTP(@Body VerifyOTPReqest verifyOTPReqest);

}
