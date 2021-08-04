package com.example.spidpay.ui.spwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.spidpay.databinding.ActivityPaymentSuccessfulBinding;


public class PaymentSuccessfulActivity extends AppCompatActivity {
    ActivityPaymentSuccessfulBinding activityPaymentSuccessfulBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPaymentSuccessfulBinding = ActivityPaymentSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(activityPaymentSuccessfulBinding.getRoot());
    }
}