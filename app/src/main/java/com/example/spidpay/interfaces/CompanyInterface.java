package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;
import com.example.spidpay.data.response.CompanyReponse;


public interface CompanyInterface extends CommonInterface{

    void onSuccess(LiveData<CompanyReponse> companyReponseLiveData);

   // void onUpdateSucess(LiveData<UpdateResponse> updateResponseLiveData);
}
