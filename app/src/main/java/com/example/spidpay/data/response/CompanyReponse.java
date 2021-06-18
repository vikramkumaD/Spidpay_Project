package com.example.spidpay.data.response;

import com.google.gson.annotations.SerializedName;

public class CompanyReponse {
    public String companyName, udyogAadhar, partnershipDeed, moa, coi, gstNO, declaration;
    @SerializedName("companyType")
    public CompanyType companyType;
}
