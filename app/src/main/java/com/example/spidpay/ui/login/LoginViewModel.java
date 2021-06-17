package com.example.spidpay.ui.login;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.LoginRepository;
import com.example.spidpay.data.request.ChangePasswordRequest;
import com.example.spidpay.data.request.LocationMap;
import com.example.spidpay.data.request.LoginRequest;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.interfaces.ForgotPassInterface;
import com.example.spidpay.interfaces.LoginInterface;
import com.google.android.gms.location.FusedLocationProviderClient;

public class LoginViewModel extends ViewModel {
    LoginInterface loginInterface;
    ForgotPassInterface forgotPassInterface;

    public String mobile_number, password, latitude, longitude, city;
    public MutableLiveData<String> mstring_mobile_number = new MutableLiveData<>();
    public MutableLiveData<Boolean> mboolean_mobile_number = new MutableLiveData<>();
    LoginRepository loginRepository;


    public void check_mobile_number(String data) {
        if (data.length() > 0)
            mboolean_mobile_number.setValue(true);
        else
            mboolean_mobile_number.setValue(false);
    }

    public void update_mobile_number() {
        mstring_mobile_number.postValue("");
    }

    public void validateLogin(View view) {
        if (mobile_number == null || mobile_number.isEmpty()) {
            loginInterface.onFailed(view.getContext().getResources().getString(R.string.mobilenoerror1));
            return;
        }

        if (!validate_Mobile_Number()) {
            loginInterface.onFailed(view.getContext().getResources().getString(R.string.mobilenoerror2));
        }


        if (password == null || password.isEmpty()) {
            loginInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror1));
            return;
        }
       /* if (password.length() < 6) {
            loginInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror2));
            return;
        }*/

        loginInterface.onServiceStart();


    }

    public void validate_Forgot_Field(View view) {
        if (!validate_Mobile_Number()) {
            loginInterface.onFailed(view.getContext().getResources().getString(R.string.mobilenoerror2));
            return;
        }

        forgotPassInterface.onForgotStart();
    }

    public boolean validate_Mobile_Number() {
        if (mobile_number.length() <= 9) {
            return false;
        } else {
            return true;
        }
    }


    public void getLoginResponse() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.userName = mobile_number;
        loginRequest.password = password;

        LocationMap locationMap = new LocationMap();
        locationMap.Device_IMEINO = "";
        locationMap.Device_IP = "";
        locationMap.Langitutde = latitude;
        locationMap.Lattitude = longitude;
        locationMap.location = city;
        loginRequest.locationMap = locationMap;

        LiveData<LoginResponse> responseLiveData = loginRepository.getLoginResposne(loginRequest);

        loginInterface.onSuccess(responseLiveData);

    }

    public void getResetPassword() {
        ChangePasswordRequest resetPass = new ChangePasswordRequest();
        resetPass.password = password;
        resetPass.userID=mobile_number;

        LiveData<CommonResponse> commonResponseLiveData = loginRepository.getResetPass(resetPass);
        forgotPassInterface.onForgotSuccess(commonResponseLiveData);

    }
}
