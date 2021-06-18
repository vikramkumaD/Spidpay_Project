package com.example.spidpay.data.request;

import com.example.spidpay.data.response.CompanyReponse;
import com.google.gson.annotations.SerializedName;

public class UpdateCompanyRequest {

    public String userId;

    @SerializedName("companyInfo")
    public CompanyReponse companyReponse;
}
