package com.example.spidpay.ui.login;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.LoginRepository;
import com.example.spidpay.data.request.LocationMap;
import com.example.spidpay.data.request.LoginRequest;
import com.example.spidpay.data.request.ResetPassword;
import com.example.spidpay.data.request.VerifyOTPReqest;
import com.example.spidpay.data.response.BooleanResponse;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.data.response.VerifyOTPResponse;
import com.example.spidpay.interfaces.ForgotPassInterface;
import com.example.spidpay.interfaces.LoginInterface;
import com.example.spidpay.util.Constant;

public class LoginViewModel extends ViewModel {
    LoginInterface loginInterface;
    ForgotPassInterface forgotPassInterface;
    public String mobile_number, password, latitude, longitude, city, verify_username, OTP, forgot_password, forgot_confirm_passwprd;
    public final MutableLiveData<String> mstring_mobile_number = new MutableLiveData<>();
    public final MutableLiveData<Boolean> mboolean_mobile_number = new MutableLiveData<>();
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
        if (validate_Mobile_Number(mobile_number)) {
            loginInterface.onFailed(view.getContext().getResources().getString(R.string.mobilenoerror2));
            return;
        }


        if (password == null || password.isEmpty()) {
            loginInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror1));
            return;
        }
        if (password.length() <= 8) {
            loginInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror2));
            return;
        }
        if (Constant.isValidPassword(password)) {
            loginInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror3));
            return;
        }

        loginInterface.onServiceStart();
    }

    public boolean validate_Mobile_Number(String number) {
        return number.length() <= 9;
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

    public void verifyUserName() {
        LiveData<BooleanResponse> commonResponseLiveData = loginRepository.verifyUserName(verify_username);
        forgotPassInterface.onForgotVerifySuccess(commonResponseLiveData);
    }

    public void validate_verifyUserName(View view) {

        if (verify_username == null || verify_username.equals("")) {
            forgotPassInterface.onForgotFailed(view.getContext().getResources().getString(R.string.entermobilenumber));
            return;
        }

        if (validate_Mobile_Number(verify_username)) {
            forgotPassInterface.onForgotFailed(view.getContext().getResources().getString(R.string.mobilenoerror2));
            return;
        }
        forgotPassInterface.onForgotStart();
    }

    public void validate_OTP_Field(View view) {
        if (OTP == null || OTP.equals("")) {
            forgotPassInterface.onForgotFailed(view.getResources().getString(R.string.enterotp));
            return;
        }
        forgotPassInterface.onForgotStart();
    }

    public void getVerifyOTP() {
        VerifyOTPReqest verifyOTPReqest = new VerifyOTPReqest();
        verifyOTPReqest.mobileNo = verify_username;
        verifyOTPReqest.userName = verify_username;
        verifyOTPReqest.mobileOTP = OTP;

        LiveData<VerifyOTPResponse> verifyOTPResponseLiveData = loginRepository.getVerifyOTP(verifyOTPReqest);
        forgotPassInterface.onVerifyOTPSuccess(verifyOTPResponseLiveData);
    }

    public void validate_Forgot_Password_Field(View view) {
        if (forgot_password == null || forgot_confirm_passwprd == null || forgot_password.equals("") || forgot_confirm_passwprd.equals("")) {
            forgotPassInterface.onForgotFailed(view.getContext().getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        if (Constant.isValidPassword(forgot_password)) {
            forgotPassInterface.onForgotFailed(view.getContext().getResources().getString(R.string.passworderror3));
            return;
        }

        if (!forgot_password.equals(forgot_confirm_passwprd)) {
            forgotPassInterface.onForgotFailed(view.getContext().getResources().getString(R.string.passwordnotmatch));
            return;
        }

        forgotPassInterface.onForgotStart();
    }

    public void getResetPassword() {
        ResetPassword resetPass = new ResetPassword();
        resetPass.password = forgot_password;
        resetPass.userName = verify_username;
        LiveData<CommonResponse> commonResponseLiveData = loginRepository.getResetPass(resetPass);
        forgotPassInterface.onForgotResetSuccess(commonResponseLiveData);

    }


}
