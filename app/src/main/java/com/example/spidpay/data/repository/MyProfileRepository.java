package com.example.spidpay.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.UpdateAddressRequest;
import com.example.spidpay.data.request.UpdateBankInfoRequest;
import com.example.spidpay.data.request.UpdateCompanyRequest;
import com.example.spidpay.data.request.UpdateKYCRequest;
import com.example.spidpay.data.response.BankDetailsResponse;
import com.example.spidpay.data.response.CompanyReponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.MyAddressResponse;
import com.example.spidpay.data.response.MyProfileResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.interfaces.BankInteface;
import com.example.spidpay.interfaces.KYCInterface;
import com.example.spidpay.interfaces.MyProfileInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileRepository {
    final Context context;
    KYCInterface kycInterface;
    MyProfileInterface myProfileInterface;
    BankInteface bankInteface;

    public MyProfileRepository(Context context, BankInteface bankInteface) {
        this.context = context;
        this.bankInteface = bankInteface;
    }

    public MyProfileRepository(Context context, MyProfileInterface myProfileInterface) {
        this.context = context;
        this.myProfileInterface = myProfileInterface;
    }

    public MyProfileRepository(Context context, KYCInterface kycInterface) {
        this.context = context;
        this.kycInterface = kycInterface;
    }


    public MutableLiveData<MyProfileResponse> getMyProfile(String userid) {
        MutableLiveData<MyProfileResponse> myProfileResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<MyProfileResponse> myProfileResponseCall = retrofitInterface.getUserProfile(userid);
        myProfileResponseCall.enqueue(new Callback<MyProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<MyProfileResponse> call, @NotNull Response<MyProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myProfileResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            myProfileInterface.onFailed(error);
                        } else {
                            myProfileInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        myProfileInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MyProfileResponse> call, @NotNull Throwable t) {
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
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<MyAddressResponse> myProfileResponseCall = retrofitInterface.getUserAddress(userid, "address");
        myProfileResponseCall.enqueue(new Callback<MyAddressResponse>() {
            @Override
            public void onResponse(@NotNull Call<MyAddressResponse> call, @NotNull Response<MyAddressResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAddressResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            myProfileInterface.onFailed(error);
                        } else {
                            myProfileInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        myProfileInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MyAddressResponse> call, @NotNull Throwable t) {
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
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<KYCResponse> myProfileResponseCall = retrofitInterface.getKYCInfo(userid, "kyc-info");
        myProfileResponseCall.enqueue(new Callback<KYCResponse>() {
            @Override
            public void onResponse(@NotNull Call<KYCResponse> call, @NotNull Response<KYCResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    kycResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            kycInterface.onFailed(error);
                        } else {
                            kycInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        kycInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<KYCResponse> call, @NotNull Throwable t) {
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
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<CompanyReponse> myProfileResponseCall = retrofitInterface.getCompany(userid, "company-info");
        myProfileResponseCall.enqueue(new Callback<CompanyReponse>() {
            @Override
            public void onResponse(@NotNull Call<CompanyReponse> call, @NotNull Response<CompanyReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    kycResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            kycInterface.onFailed(error);
                        } else {
                            kycInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        kycInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CompanyReponse> call, @NotNull Throwable t) {
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
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<UpdateResponse> myProfileResponseCall = retrofitInterface.updateAddress(updateAddressRequest);
        myProfileResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateResponse> call, @NotNull Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAddressResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            myProfileInterface.onFailed(error);
                        } else {
                            myProfileInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        myProfileInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateResponse> call, @NotNull Throwable t) {
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
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<UpdateResponse> myProfileResponseCall = retrofitInterface.updateKYC(updateKYCRequest);
        myProfileResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateResponse> call, @NotNull Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAddressResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            kycInterface.onFailed(error);
                        } else {
                            kycInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        kycInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateResponse> call, @NotNull Throwable t) {
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
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<UpdateResponse> myProfileResponseCall = retrofitInterface.updateCompany(updateKYCRequest);
        myProfileResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateResponse> call, @NotNull Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAddressResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            kycInterface.onFailed(error);
                        } else {
                            kycInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        kycInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateResponse> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    kycInterface.onFailed("No Internet");
                } else {
                    kycInterface.onFailed(t.getMessage());
                }
            }
        });
        return myAddressResponseMutableLiveData;
    }

    public MutableLiveData<BankDetailsResponse> getBankDetail(String userid) {
        MutableLiveData<BankDetailsResponse> detailsResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<BankDetailsResponse> myProfileResponseCall = retrofitInterface.getBankInfo(userid, "bank-info");
        myProfileResponseCall.enqueue(new Callback<BankDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<BankDetailsResponse> call, @NotNull Response<BankDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detailsResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            bankInteface.onFailed(error);
                        } else {
                            bankInteface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        bankInteface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<BankDetailsResponse> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    bankInteface.onFailed("No Internet");
                } else {
                    bankInteface.onFailed(t.getMessage());
                }
            }
        });
        return detailsResponseMutableLiveData;

    }

    public MutableLiveData<UpdateResponse> updateBankInfo(UpdateBankInfoRequest updateKYCRequest) {
        MutableLiveData<UpdateResponse> myAddressResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<UpdateResponse> myProfileResponseCall = retrofitInterface.updateBankInfo(updateKYCRequest);
        myProfileResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateResponse> call, @NotNull Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAddressResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            bankInteface.onFailed(error);
                        } else {
                            bankInteface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        bankInteface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateResponse> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    bankInteface.onFailed("No Internet");
                } else {
                    bankInteface.onFailed(t.getMessage());
                }
            }
        });
        return myAddressResponseMutableLiveData;
    }


}
