package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.UpdateAddressRequest;
import com.example.spidpay.data.request.UpdateCompanyRequest;
import com.example.spidpay.data.request.UpdateKYCRequest;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.CompanyReponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.MyAddressResponse;
import com.example.spidpay.data.response.MyProfileResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.interfaces.CommonInterface;
import com.example.spidpay.interfaces.CompanyInterface;
import com.example.spidpay.interfaces.KYCInterface;
import com.example.spidpay.interfaces.MyProfileInterface;
import com.example.spidpay.util.NoInternetException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileRepository {
    Context context;
    KYCInterface kycInterface;
    MyProfileInterface myProfileInterface;
    CompanyInterface companyInterface;


    public MyProfileRepository(Context context, MyProfileInterface myProfileInterface) {
        this.context = context;
        this.myProfileInterface = myProfileInterface;
    }

    public MyProfileRepository(Context context, KYCInterface kycInterface) {
        this.context = context;
        this.kycInterface = kycInterface;
    }

    public MyProfileRepository(Context context, CompanyInterface companyInterface) {
        this.context = context;
        this.companyInterface = companyInterface;
    }

    public MutableLiveData<MyProfileResponse> getMyProfile(String userid) {
        MutableLiveData<MyProfileResponse> myProfileResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<MyProfileResponse> myProfileResponseCall = retrofitInterface.getUserProfile(userid);
        myProfileResponseCall.enqueue(new Callback<MyProfileResponse>() {
            @Override
            public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myProfileResponseMutableLiveData.postValue(response.body());
                } else {
                    myProfileInterface.onFailed("Server error");
                }
            }

            @Override
            public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    myProfileInterface.onFailed("No Internet");
                } else {
                    myProfileInterface.onFailed(t.getMessage());
                }
            }
        });


        return myProfileResponseMutableLiveData;

    }

    public MutableLiveData<MyAddressResponse> getMyAddress(String userid) {
        MutableLiveData<MyAddressResponse> myAddressResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<MyAddressResponse> myProfileResponseCall = retrofitInterface.getUserAddress(userid, "address");
        myProfileResponseCall.enqueue(new Callback<MyAddressResponse>() {
            @Override
            public void onResponse(Call<MyAddressResponse> call, Response<MyAddressResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAddressResponseMutableLiveData.postValue(response.body());
                } else {
                    myProfileInterface.onFailed("Server error");
                }
            }

            @Override
            public void onFailure(Call<MyAddressResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    myProfileInterface.onFailed("No Internet");
                } else {
                    myProfileInterface.onFailed(t.getMessage());
                }
            }
        });
        return myAddressResponseMutableLiveData;
    }

    public MutableLiveData<KYCResponse> getKYCInfo(String userid) {
        MutableLiveData<KYCResponse> kycResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<KYCResponse> myProfileResponseCall = retrofitInterface.getKYCInfo(userid, "kyc-info");
        myProfileResponseCall.enqueue(new Callback<KYCResponse>() {
            @Override
            public void onResponse(Call<KYCResponse> call, Response<KYCResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    kycResponseMutableLiveData.postValue(response.body());
                } else {
                    kycInterface.onFailed("Server error");
                }
            }

            @Override
            public void onFailure(Call<KYCResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    kycInterface.onFailed("No Internet");
                } else {
                    kycInterface.onFailed(t.getMessage());
                }
            }
        });
        return kycResponseMutableLiveData;
    }

    public MutableLiveData<CompanyReponse> getCompanyInfo(String userid) {
        MutableLiveData<CompanyReponse> kycResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<CompanyReponse> myProfileResponseCall = retrofitInterface.getCompany(userid, "company-info");
        myProfileResponseCall.enqueue(new Callback<CompanyReponse>() {
            @Override
            public void onResponse(Call<CompanyReponse> call, Response<CompanyReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    kycResponseMutableLiveData.postValue(response.body());
                } else {
                    kycInterface.onFailed("Server error");
                }
            }

            @Override
            public void onFailure(Call<CompanyReponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    kycInterface.onFailed("No Internet");
                } else {
                    kycInterface.onFailed(t.getMessage());
                }
            }
        });
        return kycResponseMutableLiveData;
    }

    public MutableLiveData<UpdateResponse> updateAddress(UpdateAddressRequest updateAddressRequest) {
        MutableLiveData<UpdateResponse> myAddressResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<UpdateResponse> myProfileResponseCall = retrofitInterface.updateAddress(updateAddressRequest);
        myProfileResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAddressResponseMutableLiveData.postValue(response.body());
                } else {
                    myProfileInterface.onFailed("Server error");
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    myProfileInterface.onFailed("No Internet");
                } else {
                    myProfileInterface.onFailed(t.getMessage());
                }
            }
        });
        return myAddressResponseMutableLiveData;
    }

    public MutableLiveData<UpdateResponse> updateKYC(UpdateKYCRequest updateKYCRequest) {
        MutableLiveData<UpdateResponse> myAddressResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<UpdateResponse> myProfileResponseCall = retrofitInterface.updateKYC(updateKYCRequest);
        myProfileResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAddressResponseMutableLiveData.postValue(response.body());
                } else {
                    kycInterface.onFailed("Server error");
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    kycInterface.onFailed("No Internet");
                } else {
                    kycInterface.onFailed(t.getMessage());
                }
            }
        });
        return myAddressResponseMutableLiveData;
    }

    public MutableLiveData<UpdateResponse> updateCompany(UpdateCompanyRequest updateKYCRequest) {
        MutableLiveData<UpdateResponse> myAddressResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context).create(RetrofitInterface.class);
        Call<UpdateResponse> myProfileResponseCall = retrofitInterface.updateCompany(updateKYCRequest);
        myProfileResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAddressResponseMutableLiveData.postValue(response.body());
                } else {
                    kycInterface.onFailed("Server error");
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                if (t instanceof NoInternetException) {
                    kycInterface.onFailed("No Internet");
                } else {
                    kycInterface.onFailed(t.getMessage());
                }
            }
        });
        return myAddressResponseMutableLiveData;
    }

}
