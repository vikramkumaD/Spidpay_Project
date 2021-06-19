package com.example.spidpay.ui.profile;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.MyProfileRepository;
import com.example.spidpay.data.repository.RegisterRepository;
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.data.request.UpdateAddressRequest;
import com.example.spidpay.data.request.UpdateBankInfoRequest;
import com.example.spidpay.data.request.UpdateCompanyRequest;
import com.example.spidpay.data.request.UpdateKYCRequest;
import com.example.spidpay.data.response.BankDetailsResponse;
import com.example.spidpay.data.response.CompanyReponse;
import com.example.spidpay.data.response.InterrestedforResponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.MyAddressResponse;
import com.example.spidpay.data.response.MyProfileResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.interfaces.BankInteface;
import com.example.spidpay.interfaces.KYCInterface;
import com.example.spidpay.interfaces.MyProfileInterface;
import com.example.spidpay.util.Constant;

import java.util.List;

public class MyProfileViewModel extends ViewModel {

    public StaticRepository staticRepository;
    public MyProfileRepository myProfileRepository;
    public MyProfileInterface myProfileInterface;
    public BankInteface bankInteface;
    public KYCInterface kycInterface;
    public String userid, code, static_value;


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
        kycInterface.onKYCSuccess(kycResponseLiveData);
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
        UpdateCompanyRequest.Company company = new UpdateCompanyRequest.Company();
        company.companyName = companyReponse.companyName;
        company.coi = companyReponse.coi;
        company.companyType = code;
        company.gstNO = companyReponse.gstNO;
        company.udyogAadhar = companyReponse.udyogAadhar;
        company.moa = companyReponse.moa;
        company.partnershipDeed = companyReponse.partnershipDeed;
        company.declaration = companyReponse.declaration;
        updateCompanyInfo.company = company;

        LiveData<UpdateResponse> updateResponseLiveData = myProfileRepository.updateCompany(updateCompanyInfo);
        kycInterface.onCompanyUpdateSucess(updateResponseLiveData);
    }

    public void getBankInfo(String userid) {
        bankInteface.onServiceStart();
        LiveData<BankDetailsResponse> bankDetailsResponseLiveData = myProfileRepository.getBankDetail(userid);
        bankInteface.onSuccess(bankDetailsResponseLiveData);
    }

    public void validate_BankInfo(View view, BankDetailsResponse bankDetailsResponse) {
        /*if (bankDetailsResponse==null || bankDetailsResponse.accountHolderName == null || bankDetailsResponse.accountHolderName.equals("") || bankDetailsResponse.accountNo == 0 || bankDetailsResponse.ifscCode == null || bankDetailsResponse.ifscCode.equals("")) {
            bankInteface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;
        }
*/
        bankInteface.onServiceStart();
        updateBankInfo(bankDetailsResponse);
    }

    public void updateBankInfo(BankDetailsResponse bankDetailsResponse) {
        UpdateBankInfoRequest updateBankInfoRequest = new UpdateBankInfoRequest();
        updateBankInfoRequest.userId = userid;
        UpdateBankInfoRequest.BanKRequest banKRequest = new UpdateBankInfoRequest.BanKRequest();
        banKRequest.accountHolderName = bankDetailsResponse.accountHolderName;
        banKRequest.accountNo = bankDetailsResponse.accountNo;
        banKRequest.ifscCode = bankDetailsResponse.ifscCode;
        banKRequest.branchName = bankDetailsResponse.branchName;
        bankDetailsResponse.branchName = code;
        updateBankInfoRequest.banKRequest = banKRequest;
        LiveData<UpdateResponse> updateResponseLiveData = myProfileRepository.updateBankInfo(updateBankInfoRequest);
        bankInteface.onUpdateSuccess(updateResponseLiveData);
    }

    public LiveData<List<InterrestedforResponse>> getStaticData() {
        LiveData<List<InterrestedforResponse>> interrestedforliveData = staticRepository.getStaticData(static_value, Constant.ROLE_INTERRESTEDFOR);
        return interrestedforliveData;
    }

}
