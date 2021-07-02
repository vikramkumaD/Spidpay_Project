package com.example.spidpay.data.repository;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.LoginRequest;
import com.example.spidpay.data.request.ResetPassword;
import com.example.spidpay.data.request.VerifyOTPReqest;
import com.example.spidpay.data.response.BooleanResponse;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.data.response.UserData;
import com.example.spidpay.data.response.VerifyOTPResponse;
import com.example.spidpay.db.UserDao;
import com.example.spidpay.interfaces.ForgotPassInterface;
import com.example.spidpay.interfaces.LoginInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    Context context;
    LoginInterface loginInterface;
    ForgotPassInterface forgotPassInterface;
    UserDao userDao;



    public LoginRepository(Context context, LoginInterface loginInterface, ForgotPassInterface forgotPassInterface, UserDao userDao) {
        this.context = context;
        this.loginInterface = loginInterface;
        this.forgotPassInterface = forgotPassInterface;
        this.userDao = userDao;
    }

    public MutableLiveData<LoginResponse> getLoginResposne(LoginRequest loginRequest) {
        MutableLiveData<LoginResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_API).create(RetrofitInterface.class);
        Call<LoginResponse> call = retrofitInterface.user_login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    insertUserData(response.body().userData);
                    responseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            loginInterface.onFailed(error);
                        } else {
                            loginInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        loginInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    loginInterface.onFailed("No Internet");
                } else {
                    loginInterface.onFailed(t.getMessage());
                }
            }
        });

        return responseMutableLiveData;
    }

    public MutableLiveData<CommonResponse> getResetPass(ResetPassword changePasswordRequest) {
        MutableLiveData<CommonResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_API).create(RetrofitInterface.class);
        Call<CommonResponse> call = retrofitInterface.resetPass(changePasswordRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NotNull Call<CommonResponse> call, @NotNull Response<CommonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            forgotPassInterface.onForgotFailed(error);
                        } else {
                            forgotPassInterface.onForgotFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        forgotPassInterface.onForgotFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    forgotPassInterface.onForgotFailed("No Internet");
                } else {
                    forgotPassInterface.onForgotFailed(t.getMessage());
                }
            }
        });
        return responseMutableLiveData;
    }

    public MutableLiveData<BooleanResponse> verifyUserName(String username) {
        MutableLiveData<BooleanResponse> commonResponseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_API).create(RetrofitInterface.class);
        Call<BooleanResponse> call = retrofitInterface.verifyUsername(username);
        call.enqueue(new Callback<BooleanResponse>() {
            @Override
            public void onResponse(@NotNull Call<BooleanResponse> call, @NotNull Response<BooleanResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commonResponseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            forgotPassInterface.onForgotFailed(error);
                        } else {
                            forgotPassInterface.onForgotFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        loginInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<BooleanResponse> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    forgotPassInterface.onForgotFailed("No Internet");
                } else {
                    forgotPassInterface.onForgotFailed(t.getMessage());
                }
            }
        });
        return commonResponseMutableLiveData;
    }

    public MutableLiveData<VerifyOTPResponse> getVerifyOTP(VerifyOTPReqest loginRequest) {
        MutableLiveData<VerifyOTPResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_API).create(RetrofitInterface.class);
        Call<VerifyOTPResponse> call = retrofitInterface.verifyOTP(loginRequest);
        call.enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(@NotNull Call<VerifyOTPResponse> call, @NotNull Response<VerifyOTPResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            forgotPassInterface.onForgotFailed(error);
                        } else {
                            forgotPassInterface.onForgotFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        forgotPassInterface.onForgotFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<VerifyOTPResponse> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    forgotPassInterface.onForgotFailed("No Internet");
                } else {
                    forgotPassInterface.onForgotFailed(t.getMessage());
                }
            }
        });

        return responseMutableLiveData;
    }

    public void insertUserData(UserData userData) {
       long l= userDao.insertUser(userData);
        Log.e("ok",String.valueOf(l));
    }


}
