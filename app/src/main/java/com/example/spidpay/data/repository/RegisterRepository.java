package com.example.spidpay.data.repository;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.RegisterRequest;
import com.example.spidpay.data.response.UserData;
import com.example.spidpay.db.UserDao;
import com.example.spidpay.interfaces.RegisterInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    final Context context;
    final RegisterInterface registerInterface;
    final UserDao userDao;

    public RegisterRepository(Context context, RegisterInterface registerInterface, UserDao userDao) {
        this.context = context;
        this.registerInterface = registerInterface;
        this.userDao = userDao;
    }

    public MutableLiveData<UserData> getRegisterResponse(RegisterRequest registerRequest) {
        MutableLiveData<UserData> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_API).create(RetrofitInterface.class);
        Call<UserData> call = retrofitInterface.user_onBoarding(registerRequest);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NotNull Call<UserData> call, @NotNull Response<UserData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    insertUserData(response.body());
                    responseMutableLiveData.postValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            String error = Constant.parseErrorBodyofRetrofit(errorBody);
                            registerInterface.onFailed(error);
                        } else {
                            registerInterface.onFailed(Constant.Server_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        registerInterface.onFailed(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserData> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    registerInterface.onFailed("No Internet");
                } else {
                    registerInterface.onFailed(t.getMessage());
                }
            }
        });
        return responseMutableLiveData;
    }

    public void insertUserData(UserData userData) {
        new Thread(() -> {
            long l = userDao.insertUser(userData);
            Log.e("ok", String.valueOf(l));
        }).start();
    }
}
