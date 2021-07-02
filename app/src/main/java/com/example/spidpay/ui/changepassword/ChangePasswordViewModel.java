package com.example.spidpay.ui.changepassword;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.ChangePasswordRepository;
import com.example.spidpay.data.request.ChangePasswordRequest;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.interfaces.ChangePasswordInterface;
import com.example.spidpay.util.Constant;

public class ChangePasswordViewModel extends ViewModel {

    public String string_old_password, string_new_password, string_confirm_password;
    ChangePasswordRepository changePasswordRepository;
    ChangePasswordInterface changePasswordInterface;


    public void validateChangePassField(View view) {
        if (string_old_password == null || string_new_password == null || string_confirm_password == null || string_old_password.equals("") || string_new_password.equals("") || string_confirm_password.equals("")) {
            changePasswordInterface.onFailed(view.getContext().getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        if(string_new_password.length()<=8)
        {
            changePasswordInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror2));
            return;
        }


        if (Constant.isValidPassword(string_new_password)) {
            changePasswordInterface.onFailed(view.getContext().getResources().getString(R.string.passworderror3));
            return;
        }

        if (!string_new_password.equals(string_confirm_password)) {
            changePasswordInterface.onFailed(view.getContext().getResources().getString(R.string.passwordnotmatch));
            return;

        }

        changePasswordInterface.onServiceStart();

    }


    public void getChangePassword(String userid) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.oldpassword = string_old_password;
        changePasswordRequest.password = string_new_password;
        changePasswordRequest.userID=userid;

        LiveData<CommonResponse> commonResponseLiveData = changePasswordRepository.changePassword(changePasswordRequest);
        changePasswordInterface.onSuccess(commonResponseLiveData);
    }
}
