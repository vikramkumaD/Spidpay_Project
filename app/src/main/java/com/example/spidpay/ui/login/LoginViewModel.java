package com.example.spidpay.ui.login;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.interfaces.CommonInterface;

public class LoginViewModel extends ViewModel {
    CommonInterface commonInterface;
    public String mobile_number, password;
    public MutableLiveData<String> mstring_mobile_number = new MutableLiveData<>();
    public MutableLiveData<Boolean> mboolean_mobile_number = new MutableLiveData<>();
    public void check_mobile_number(String data) {
        if (data.length() > 0)
            mboolean_mobile_number.postValue(true);
        else
            mboolean_mobile_number.postValue(false);
    }

    public void update_mobile_number() {
        mstring_mobile_number.postValue("");
    }


    public void validateLogin(View view) {
        if (mobile_number == null || mobile_number.isEmpty()) {
            commonInterface.onFailed(view.getContext().getResources().getString(R.string.mobilenoerror1));
            return;
        }
        if (mobile_number.length() <= 9) {
            commonInterface.onFailed(view.getContext().getResources().getString(R.string.mobilenoerror2));
            return;
        }
        if (password == null || password.isEmpty()) {
            commonInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror1));
            return;
        }
        if (password.length() < 6) {
            commonInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror2));
            return;
        }
        commonInterface.onSuccess();
    }
}
