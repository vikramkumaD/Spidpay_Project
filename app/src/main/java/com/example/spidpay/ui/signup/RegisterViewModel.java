package com.example.spidpay.ui.signup;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.interfaces.CommonInterface;

public class RegisterViewModel extends ViewModel {

    CommonInterface commonInterface;
    public String password,fullname,mobilenumber;
   // public MutableLiveData<String> string_register_full_name = new MutableLiveData<>();
    public MutableLiveData<Boolean> boolean_register_full_name = new MutableLiveData<>();
    public MutableLiveData<Boolean> boolean_register_mobile_number = new MutableLiveData<>();
   // public MutableLiveData<String> string_register_mobile_number = new MutableLiveData<>();

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
        fullname="";
    }

    public void clear_mobile_number() {
        mobilenumber="";
    }


    public void validate_signupView(View view) {
        if (fullname == null || fullname.isEmpty() || mobilenumber == null || mobilenumber.isEmpty()|| password == null || password.isEmpty()) {
            commonInterface.onFailed(view.getContext().getResources().getString(R.string.filedcannotbeblank));
            return;
        }
        if (mobilenumber.length() <= 9) {
            commonInterface.onFailed(view.getContext().getResources().getString(R.string.mobilenoerror2));
            return;
        }
        commonInterface.onSuccess();
    }

}
