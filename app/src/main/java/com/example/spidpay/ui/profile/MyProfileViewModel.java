package com.example.spidpay.ui.profile;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.MyProfileRepository;
import com.example.spidpay.data.request.UpdateAddressRequest;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.MyAddressResponse;
import com.example.spidpay.data.response.MyProfileResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.interfaces.KYCInterface;
import com.example.spidpay.interfaces.MyProfileInterface;

public class MyProfileViewModel extends ViewModel {

    public MyProfileRepository myProfileRepository;
    public MyProfileInterface myProfileInterface;
    public KYCInterface kycInterface;
    public String userid;


    public void getMyProfile(String userid) {
        myProfileInterface.onServiceStart();
        LiveData<MyProfileResponse> myProfileResponseLiveData = myProfileRepository.getMyProfile(userid);
        myProfileInterface.onProfileSuccess(myProfileResponseLiveData);
    }


    public void getMyAddress(String userid) {
        myProfileInterface.onServiceStart();
        LiveData<MyAddressResponse> myProfileResponseLiveData = myProfileRepository.getMyAddress(userid);
        myProfileInterface.onAddressSuccess(myProfileResponseLiveData);
    }

    public void validate_edit_Address(View view, MyAddressResponse myAddressResponse) {
        if (myAddressResponse.address1 == null || myAddressResponse.address1.equals("") || myAddressResponse.address2 == null || myAddressResponse.address2.equals("")
                || myAddressResponse.address3 == null || myAddressResponse.address3.equals("") || myAddressResponse.pinCode == null || myAddressResponse.pinCode.equals("") ||
                myAddressResponse.state == null || myAddressResponse.state.equals("") || myAddressResponse.country == null || myAddressResponse.equals("")) {
            myProfileInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        myProfileInterface.onServiceStart();
        updateAddress(myAddressResponse);
    }

    public void updateAddress(MyAddressResponse myAddressResponse) {
        UpdateAddressRequest updateAddressRequest = new UpdateAddressRequest();
        updateAddressRequest.userId = userid;
        updateAddressRequest.myAddressResponse = myAddressResponse;
        LiveData<UpdateResponse> commonResponseLiveData = myProfileRepository.updateAddress(updateAddressRequest);
        myProfileInterface.onUpdateAddress(commonResponseLiveData);
    }

    public void getKYCInfo(String userid) {
        kycInterface.onServiceStart();
        LiveData<KYCResponse> kycResponseLiveData = myProfileRepository.getKYCInfo(userid);
        kycInterface.onSuccess(kycResponseLiveData);
    }
}
