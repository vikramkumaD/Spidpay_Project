package com.example.spidpay.data.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.example.spidpay.data.RetrofitClient;
import com.example.spidpay.data.RetrofitInterface;
import com.example.spidpay.data.request.RegisterRequest;
import com.example.spidpay.data.response.RegisterResponse;
import com.example.spidpay.interfaces.RegisterInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NoInternetException;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    Context context;
    RegisterInterface registerInterface;

    public RegisterRepository(Context context, RegisterInterface registerInterface) {
        this.context = context;
        this.registerInterface = registerInterface;
    }

    public MutableLiveData<RegisterResponse> getRegisterResponse(RegisterRequest registerRequest) {
        MutableLiveData<RegisterResponse> responseMutableLiveData = new MutableLiveData<>();
        RetrofitInterface retrofitInterface = RetrofitClient.GetRetrofitClient(context, Constant.USER_URL).create(RetrofitInterface.class);
        Call<RegisterResponse> call = retrofitInterface.user_onBoarding(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NotNull Call<RegisterResponse> call, @NotNull Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
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
            public void onFailure(@NotNull Call<RegisterResponse> call, @NotNull Throwable t) {
                if (t instanceof NoInternetException) {
                    registerInterface.onFailed("No Internet");
                } else {
                    registerInterface.onFailed(t.getMessage());
                }
            }
        });
        return responseMutableLiveData;
    }
}
