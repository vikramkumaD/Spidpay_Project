package com.example.spidpay.ui.signup;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.RegisterRepository;
import com.example.spidpay.data.request.RegisterRequest;
import com.example.spidpay.data.request.Register_UserInfo;
import com.example.spidpay.data.response.RegisterResponse;
import com.example.spidpay.interfaces.RegisterInterface;

public class RegisterViewModel extends ViewModel {

    RegisterInterface registerInterface;
    public String password, fullname, mobilenumber;
    // public MutableLiveData<String> string_register_full_name = new MutableLiveData<>();
    public MutableLiveData<Boolean> boolean_register_full_name = new MutableLiveData<>();
    public MutableLiveData<Boolean> boolean_register_mobile_number = new MutableLiveData<>();
    // public MutableLiveData<String> string_register_mobile_number = new MutableLiveData<>();

    RegisterRepository registerRepository;

    public void validate_full_name(String name) {
        if (name.length() > 0)
            boolean_register_full_name.setValue(true);
        else
            boolean_register_full_name.setValue(false);
    }

    public void validate_mobile_number(String name) {
        if (name.length() > 0)
            boolean_register_mobile_number.setValue(true);
        else
            boolean_register_mobile_number.setValue(false);
    }


    public void clear_full_name() {
        fullname = "";
    }

    public void clear_mobile_number() {
        mobilenumber = "";
    }


    public void validate_signupView(View view) {
        if (fullname == null || fullname.isEmpty() || mobilenumber == null || mobilenumber.isEmpty() || password == null || password.isEmpty()) {
            registerInterface.onFailed(view.getContext().getResources().getString(R.string.filedcannotbeblank));
            return;
        }
        if (mobilenumber.length() <= 9) {
            registerInterface.onFailed(view.getContext().getResources().getString(R.string.mobilenoerror2));
            return;
        }

        registerInterface.onServiceStart();

    }

    public void getRegisterResponse() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.sendOTP = "N";
        registerRequest.webPortal = "N";

        Register_UserInfo register_userInfo = new Register_UserInfo();
        register_userInfo.email = "india@gmmail.com";
        register_userInfo.firstName = fullname;
        register_userInfo.lastName = "";
        register_userInfo.mobileNo = mobilenumber;
        register_userInfo.password = password;
        register_userInfo.userType = "1001";
        register_userInfo.domainCode = "SPIDPAY";
        register_userInfo.parentUserId = "d103f219-6e1c-4096-bc4b-8a4a1ce3b138";

        registerRequest.register_userInfo = register_userInfo;

       LiveData<RegisterResponse> registerResponseLiveData= registerRepository.getRegisterResponse(registerRequest);

       registerInterface.onSuccess(registerResponseLiveData);


    }

}
