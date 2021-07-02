package com.example.spidpay.ui.addmoney.cashdeposite;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spidpay.databinding.CashDepositeActivityBinding;


public class CashDesposit_Activity extends AppCompatActivity {
    CashDepositeActivityBinding cashDepositeActivityBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cashDepositeActivityBinding = CashDepositeActivityBinding.inflate(getLayoutInflater());
        setContentView(cashDepositeActivityBinding.getRoot());


        cashDepositeActivityBinding.imgBackpress.setOnClickListener(v -> finish());
    }

}