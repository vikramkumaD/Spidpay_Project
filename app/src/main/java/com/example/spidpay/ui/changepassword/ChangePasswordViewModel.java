package com.example.spidpay.ui.changepassword;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.repository.ChangePasswordRepository;
import com.example.spidpay.data.request.ChangePasswordRequest;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.interfaces.ChangePasswordInterface;

public class ChangePasswordViewModel {

    public String string_old_password, string_new_password, string_confirm_password;
    ChangePasswordRepository changePasswordRepository;
    ChangePasswordInterface changePasswordInterface;

    public void getChangePassword() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.oldpassword = string_old_password;
        changePasswordRequest.password = string_new_password;

        LiveData<CommonResponse> commonResponseLiveData = changePasswordRepository.changePassword(changePasswordRequest);
        changePasswordInterface.onSuccess(commonResponseLiveData);

    }
}
