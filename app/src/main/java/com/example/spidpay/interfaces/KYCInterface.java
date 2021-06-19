package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;

import com.example.spidpay.data.response.CompanyReponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.UpdateResponse;

public interface KYCInterface extends CommonInterface {

    void onKYCSuccess(LiveData<KYCResponse> kycResponseLiveData);

    void onUpdateSucess(LiveData<UpdateResponse> updateResponseLiveData);

    void onCompanySuccess(LiveData<CompanyReponse> companyReponseLiveData);

    void onCompanyUpdateSucess(LiveData<UpdateResponse> updateResponseLiveData);

}
