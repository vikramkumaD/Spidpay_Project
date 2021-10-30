package com.example.spidpay.data.request;

public class BankResponse {
    public String accountHolderName, branchName, ifscCode;
    public int accountNo;

    public BankName bankName;


    public class BankName {

        public String code, description;

    }
}
