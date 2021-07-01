package com.example.spidpay.ui.addmoney.cashdeposite;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.databinding.FragmentCashDepositBinding;


public class CashDesposit_Activity extends AppCompatActivity {
    FragmentCashDepositBinding fragmentCashDepositBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentCashDepositBinding = FragmentCashDepositBinding.inflate(getLayoutInflater());
        setContentView(fragmentCashDepositBinding.getRoot());


        fragmentCashDepositBinding.imgBackpress.setOnClickListener(v -> finish());
    }

}