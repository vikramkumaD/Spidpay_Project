package com.example.spidpay.ui.signup;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.RegisterRepository;
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.data.request.RegisterRequest;
import com.example.spidpay.data.request.Register_UserInfo;
import com.example.spidpay.data.response.InterrestedforResponse;
import com.example.spidpay.data.response.RegisterResponse;
import com.example.spidpay.interfaces.RegisterInterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.util.Constant;

import java.util.List;

public class RegisterViewModel extends ViewModel {

    StaticInterface staticInterface;
    StaticRepository staticRepository;
    RegisterInterface registerInterface;
    public String password, confirm_password, code;
    public MutableLiveData<String> string_register_first_name = new MutableLiveData<>();
    public MutableLiveData<String> string_register_last_name = new MutableLiveData<>();
    public MutableLiveData<String> string_register_mobile_number = new MutableLiveData<>();
    public MutableLiveData<String> string_register_email_Address = new MutableLiveData<>();

    RegisterRepository registerRepository;


    public void update_first_name(String name) {
        if (name.length() > 0)
            string_register_first_name.setValue(name);
        else
            string_register_first_name.setValue("");
    }

    public void clear_first_name() {
        string_register_first_name.setValue("");
    }


    public void update_last_name(String name) {
        if (name.length() > 0)
            string_register_last_name.setValue(name);
        else
            string_register_last_name.setValue("");
    }


    public void clear_last_name() {
        string_register_last_name.setValue("");
    }


    public void update_mobile_number(String name) {
        if (name.length() > 0)
            string_register_mobile_number.setValue(name);
        else
            string_register_mobile_number.setValue("");
    }

    public void update_email_Address(String name) {
        if (name.length() > 0)
            string_register_email_Address.setValue(name);
        else
            string_register_email_Address.setValue("");
    }


    public void clear_mobile_number() {
        string_register_mobile_number.setValue("");
    }

    public void clear_email() {
        string_register_email_Address.setValue("");
    }


    public void validate_signupView(View view) {
        if (string_register_first_name.getValue() == null || string_register_first_name.getValue().equals("")
                || string_register_last_name.getValue() == null || string_register_last_name.getValue().equals("")
                || string_register_mobile_number.getValue() == null || string_register_mobile_number.getValue().equals("")
                || password == null || password.equals("")
                || confirm_password == null || confirm_password.equals("")) {
            registerInterface.onFailed(view.getContext().getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        if (code == null || code.equals("")) {
            registerInterface.onFailed(view.getContext().getResources().getString(R.string.pleaseseletcinterestedfor));
            return;
        }

        if (string_register_mobile_number.getValue().length() <= 9) {
            registerInterface.onFailed(view.getContext().getResources().getString(R.string.mobilenoerror2));
            return;
        }

        if (string_register_email_Address.getValue()!=null && !string_register_email_Address.getValue().equals("") && !Constant.validate(string_register_email_Address.getValue())) {
            registerInterface.onFailed(view.getResources().getString(R.string.entervalidemail));
            return;
        }

        if (password.length() <= 8) {
            registerInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror2));
            return;
        }
        if (!Constant.isValidPassword(password)) {
            registerInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror3));
            return;
        }

        if (!password.equals(confirm_password)) {
            registerInterface.onFailed(view.getResources().getString(R.string.passwordnotmatch));
            return;
        }
        registerInterface.onServiceStart();
    }


    public void getRegisterResponse() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.sendOTP = "Y";
        registerRequest.webPortal = "N";

        Register_UserInfo register_userInfo = new Register_UserInfo();
        register_userInfo.email = string_register_email_Address.getValue();
        register_userInfo.firstName = string_register_first_name.getValue();
        register_userInfo.lastName = string_register_last_name.getValue();
        register_userInfo.mobileNo = string_register_mobile_number.getValue();
        register_userInfo.password = password;
        register_userInfo.userType = code;
        register_userInfo.domainCode = Constant.DOMAIN_NAME;
        register_userInfo.parentUserId = Constant.PARENT_ID;

        registerRequest.register_userInfo = register_userInfo;

        LiveData<RegisterResponse> registerResponseLiveData = registerRepository.getRegisterResponse(registerRequest);

        registerInterface.onSuccess(registerResponseLiveData);


    }

    public LiveData<List<InterrestedforResponse>> getInterrestedFor() {
        staticInterface.onStaticStart();
        LiveData<List<InterrestedforResponse>> interrestedforliveData = staticRepository.getStaticData(Constant.USER,Constant.ROLE_INTERRESTEDFOR);
        return interrestedforliveData;
    }

}
