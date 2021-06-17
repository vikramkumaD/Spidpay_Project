package com.example.spidpay.ui.verifyotp;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.VerifyOTPRepository;
import com.example.spidpay.data.request.VerifyOTPReqest;
import com.example.spidpay.data.response.VerifyOTPResponse;
import com.example.spidpay.interfaces.VerifyOTPInterface;

public class VerifyOTPViewModel extends ViewModel {

    public String username, OTP;
    VerifyOTPInterface verifyOTPInterface;
    VerifyOTPRepository verifyOTPRepository;


    public void validateOTPFiled(View view)
    {
        if(OTP==null || OTP.equals(""))
        {
            verifyOTPInterface.onFailed(view.getResources().getString(R.string.enterotp));
            return;
        }

        verifyOTPInterface.onServiceStart();
    }

    public void getVerifyOTP() {
        VerifyOTPReqest verifyOTPReqest = new VerifyOTPReqest();
        verifyOTPReqest.mobileNo = username;
        verifyOTPReqest.userName = username;
        verifyOTPReqest.mobileOTP=OTP;

        LiveData<VerifyOTPResponse> verifyOTPResponseLiveData= verifyOTPRepository.getVerifyOTP(verifyOTPReqest);
        verifyOTPInterface.onSuccess(verifyOTPResponseLiveData);
    }


}
