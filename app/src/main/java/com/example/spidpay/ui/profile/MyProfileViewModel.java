package com.example.spidpay.ui.profile;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.MyProfileRepository;
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
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class MyProfileViewModel extends ViewModel {

    public StaticRepository staticRepository;
    public MyProfileRepository myProfileRepository;
    public MyProfileInterface myProfileInterface;
    public BankInteface bankInteface;
    public StaticInterface staticInterface;
    public KYCInterface kycInterface;
    public String userid, code, companytype_description, static_value;


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
        if (myAddressResponse.addressline == null || myAddressResponse.addressline.equals("")
                || myAddressResponse.city == null || myAddressResponse.city.equals("") ||
                myAddressResponse.district == null || myAddressResponse.district.equals("") ||
                myAddressResponse.pinCode == null || myAddressResponse.pinCode.equals("") ||
                myAddressResponse.state == null || myAddressResponse.state.equals("")
        ) {
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

        if (kycResponse.panNo == null || kycResponse.panNo.equals("") || kycResponse.aadharNo == null || kycResponse.aadharNo.equals("")) {
            kycInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        if (!Constant.isValidPanCardNo(kycResponse.panNo)) {
            kycInterface.onFailed(view.getContext().getResources().getString(R.string.validpan));
            return;
        }

        if (!Constant.isValidAadharNumber(kycResponse.aadharNo)) {
            kycInterface.onFailed(view.getContext().getResources().getString(R.string.validaadhar));
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
                code == null || code.equals("") ||
                companyReponse.gstNO == null || companyReponse.gstNO.equals("")) {
            kycInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        if (companytype_description.equals(Constant.PRIVATE_LIMITED)) {
            if (companyReponse.coi == null || companyReponse.coi.equals("")
                    || companyReponse.moa == null || companyReponse.moa.equals("") ||
                    companyReponse.declaration == null || companyReponse.declaration.equals("")) {
                kycInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
                return;
            }
        }

        if (companytype_description.equals(Constant.SOLE_OWNER)) {
            if (companyReponse.udyogAadhar == null || companyReponse.udyogAadhar.equals("")) {
                kycInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
                return;
            }
        }

        if (companytype_description.equals(Constant.PARTNERSHIP)) {
            if (companyReponse.partnershipDeed == null || companyReponse.partnershipDeed.equals("")) {
                kycInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
                return;
            }
        }

        if (!Constant.isValidGSTNo(companyReponse.gstNO)) {
            kycInterface.onFailed(view.getContext().getResources().getString(R.string.entervalidgstno));
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


        if (bankDetailsResponse.accountHolderName == null || bankDetailsResponse.accountNo == null || bankDetailsResponse.ifscCode == null || code == null) {
            bankInteface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;
        }


        if (bankDetailsResponse.accountHolderName.equals("") && bankDetailsResponse.accountNo.equals("") && code.equals("") && bankDetailsResponse.ifscCode.equals("")) {
            bankInteface.onFailed(view.getContext().getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        if (bankDetailsResponse.accountNo.length() <= 8) {
            bankInteface.onFailed(view.getContext().getResources().getString(R.string.enterproperaccno));
            return;
        }


        if (!Constant.isValidIFSCode(bankDetailsResponse.ifscCode)) {
            bankInteface.onFailed(view.getContext().getResources().getString(R.string.validifsc));
            return;
        }


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
        banKRequest.bankName = code;

        updateBankInfoRequest.banKRequest = banKRequest;
        LiveData<UpdateResponse> updateResponseLiveData = myProfileRepository.updateBankInfo(updateBankInfoRequest);
        bankInteface.onUpdateSuccess(updateResponseLiveData);
    }

    public LiveData<List<InterrestedforResponse>> getStaticData() {
        staticInterface.onStaticStart();
        LiveData<List<InterrestedforResponse>> interrestedforliveData = staticRepository.getStaticData(static_value, Constant.ROLE_INTERRESTEDFOR);
        return interrestedforliveData;
    }

    public LiveData<List<InterrestedforResponse>> getStaticDataForAdditionalIds(Context context) {
        staticInterface.onStaticStart();
        List<InterrestedforResponse> interrestedforliveData = new ArrayList<>();
        interrestedforliveData.add(new InterrestedforResponse("999", context.getResources().getString(R.string.select)));
        interrestedforliveData.add(new InterrestedforResponse("1", context.getResources().getString(R.string.voterid_1)));
        interrestedforliveData.add(new InterrestedforResponse("2", context.getResources().getString(R.string.drivinglicense)));
        interrestedforliveData.add(new InterrestedforResponse("3", context.getResources().getString(R.string.rationcard)));
        MutableLiveData<List<InterrestedforResponse>> listMutableLiveData = new MutableLiveData<>();
        listMutableLiveData.postValue(interrestedforliveData);
        return listMutableLiveData;
    }

}
