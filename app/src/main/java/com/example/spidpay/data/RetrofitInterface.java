package com.example.spidpay.data;

import com.example.spidpay.data.request.AddMoneyRequest;
import com.example.spidpay.data.request.BankTransferRequest;
import com.example.spidpay.data.request.ChangePasswordRequest;
import com.example.spidpay.data.request.LoginRequest;
import com.example.spidpay.data.request.RegisterRequest;
import com.example.spidpay.data.request.ResetPassword;
import com.example.spidpay.data.request.TransferMoneyRequest;
import com.example.spidpay.data.request.UpdateAddressRequest;
import com.example.spidpay.data.request.UpdateBankInfoRequest;
import com.example.spidpay.data.request.UpdateCompanyRequest;
import com.example.spidpay.data.request.UpdateKYCRequest;
import com.example.spidpay.data.request.VerifyOTPReqest;
import com.example.spidpay.data.response.AddMoneyResponse;
import com.example.spidpay.data.response.AllTransactionResponse;
import com.example.spidpay.data.response.BankDetailsResponse;
import com.example.spidpay.data.response.BooleanResponse;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.CompanyReponse;
import com.example.spidpay.data.response.InterrestedforResponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.data.response.MyAddressResponse;
import com.example.spidpay.data.response.RegisterResponse;
import com.example.spidpay.data.response.MyProfileResponse;
import com.example.spidpay.data.response.TransferMoenyResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.data.response.VerifyOTPResponse;
import com.example.spidpay.data.response.WalletResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<InterrestedforResponse>> getStaticData(@Path("category") String user, @Query("role") String role);

    @Headers({"Content-Type:application/json"})
    @POST("change-password/v1")
    Call<CommonResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @Headers({"Content-Type:application/json"})
    @POST("reset-password/v1")
    Call<CommonResponse> resetPass(@Body ResetPassword changePasswordRequest);

    @GET("reset-password/v1/{userName}")
    Call<BooleanResponse> verifyUsername(@Path("userName") String userName);

    @POST("otp/v1/verify-mobile-otp")
    Call<VerifyOTPResponse> verifyOTP(@Body VerifyOTPReqest verifyOTPReqest);

    @GET("user/v1/{userId}")
    Call<MyProfileResponse> getUserProfile(@Path("userId") String user);

    @GET("user/v1/{userId}/{address}")
    Call<MyAddressResponse> getUserAddress(@Path("userId") String user, @Path("address") String address);

    @Headers({"Content-Type:application/json"})
    @PUT("user/v1/update/address")
    Call<UpdateResponse> updateAddress(@Body UpdateAddressRequest updateAddressRequest);

    @GET("user/v1/{userId}/{kyc-info}")
    Call<KYCResponse> getKYCInfo(@Path("userId") String user, @Path("kyc-info") String kycInfo);

    @Headers({"Content-Type:application/json"})
    @PUT("user/v1/update/kyc")
    Call<UpdateResponse> updateKYC(@Body UpdateKYCRequest updateKYCRequest);

    @Headers({"Content-Type:application/json"})
    @PUT("user/v1/update/company")
    Call<UpdateResponse> updateCompany(@Body UpdateCompanyRequest updateCompanyRequest);

    @GET("user/v1/{userId}/{company-info}")
    Call<CompanyReponse> getCompany(@Path("userId") String user, @Path("company-info") String company);

    @GET("user/v1/{userId}/{bank-info}")
    Call<BankDetailsResponse> getBankInfo(@Path("userId") String user, @Path("bank-info") String bank);

    @Headers({"Content-Type:application/json"})
    @PUT("user/v1/update/bank-info")
    Call<UpdateResponse> updateBankInfo(@Body UpdateBankInfoRequest updateBankInfoRequest);

    @GET("v1/{userId}")
    Call<List<WalletResponse>> getWalletResponse(@Path("userId") String userId);

    @GET("v1/wallet/{walletId}")
    Call<List<AllTransactionResponse>> getAllTransaction(@Path("walletId") String walletId, @Query("pageNumber") String pageNumber, @Query("pageSize") String pageSize);

    @POST("v1/load-money")
    Call<AddMoneyResponse> addMoney(@Body AddMoneyRequest verifyOTPRequest);

    @POST("v1/trade/payout")
    Call<TransferMoenyResponse> getTransferResponse(@Body TransferMoneyRequest transferMoneyRequest);

    @POST("v1/trade/payout")
    Call<TransferMoenyResponse> getTransferResponse(@Body BankTransferRequest transferMoneyRequest);

}
