package com.example.spidpay.ui.profile;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.MyProfileRepository;
import com.example.spidpay.data.request.UpdateAddressRequest;
import com.example.spidpay.data.request.UpdateCompanyRequest;
import com.example.spidpay.data.request.UpdateKYCRequest;
import com.example.spidpay.data.response.CompanyReponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.MyAddressResponse;
import com.example.spidpay.data.response.MyProfileResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.interfaces.CompanyInterface;
import com.example.spidpay.interfaces.KYCInterface;
import com.example.spidpay.interfaces.MyProfileInterface;

public class MyProfileViewModel extends ViewModel {

    public MyProfileRepository myProfileRepository;
    public MyProfileInterface myProfileInterface;
    public CompanyInterface companyInterface;
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

    public void validate_KYC(View view, KYCResponse kycResponse) {
        if (kycResponse.panNo == null || kycResponse.panNo.equals("") || kycResponse.aadharNo == null || kycResponse.equals("") || kycResponse.idNo == null || kycResponse.idNo.equals("")) {
            kycInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;
        }
        kycInterface.onServiceStart();
        updateKYCInfo(kycResponse);
    }

    public void updateKYCInfo(KYCResponse kycResponse) {
        UpdateKYCRequest updateKYCRequest = new UpdateKYCRequest();
        updateKYCRequest.userId = userid;
        UpdateKYCRequest.KYCRequest kycRequest = new UpdateKYCRequest.KYCRequest();
        kycRequest.aadharNo = kycResponse.aadharNo;
        kycRequest.panNo = kycResponse.panNo;
        kycRequest.idNo = kycResponse.idNo;
        kycRequest.idType = "";
        updateKYCRequest.kycResponse = kycRequest;
        LiveData<UpdateResponse> updateResponseLiveData = myProfileRepository.updateKYC(updateKYCRequest);
        kycInterface.onUpdateSucess(updateResponseLiveData);
    }

    public void getCompanyInfo(String userid) {
        kycInterface.onServiceStart();
        LiveData<CompanyReponse> liveData = myProfileRepository.getCompanyInfo(userid);
        kycInterface.onCompanySuccess(liveData);
    }

    public void validate_compnay(View view, CompanyReponse companyReponse) {
        if (companyReponse.companyName == null || companyReponse.companyName.equals("") ||
                companyReponse.companyType.description == null || companyReponse.companyType.description.equals("") ||
                companyReponse.coi == null || companyReponse.coi.equals("") || companyReponse.declaration == null || companyReponse.declaration.equals("") ||
                companyReponse.gstNO == null || companyReponse.gstNO.equals("") || companyReponse.moa == null || companyReponse.moa.equals("") ||
                companyReponse.partnershipDeed == null || companyReponse.partnershipDeed.equals("") ||
                companyReponse.udyogAadhar == null || companyReponse.udyogAadhar.equals("")) {
            kycInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;

        }

        kycInterface.onServiceStart();
        updateCompanyDeatil(companyReponse);
    }

    public void updateCompanyDeatil(CompanyReponse companyReponse) {
        UpdateCompanyRequest updateCompanyInfo = new UpdateCompanyRequest();
        updateCompanyInfo.userId = userid;
        updateCompanyInfo.companyReponse = companyReponse;
        LiveData<UpdateResponse> updateResponseLiveData = myProfileRepository.updateCompany(updateCompanyInfo);
        kycInterface.onCompanyUpdateSucess(updateResponseLiveData);
    }
}
