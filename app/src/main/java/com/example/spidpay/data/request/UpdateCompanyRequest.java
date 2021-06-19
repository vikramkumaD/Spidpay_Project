package com.example.spidpay.data.request;

import com.example.spidpay.data.response.CompanyReponse;
import com.google.gson.annotations.SerializedName;

public class UpdateCompanyRequest {

    public String userId;

    @SerializedName("companyInfo")
    public Company company;

    public static class Company {
        public String companyName, udyogAadhar, partnershipDeed, moa, coi, gstNO, declaration, companyType;
    }

}
