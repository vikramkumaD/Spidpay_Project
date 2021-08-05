package com.example.spidpay.ui.spwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.spidpay.R;
import com.example.spidpay.databinding.ActivityPaymentSuccessfulBinding;
import com.example.spidpay.ui.HostActivity;
import com.example.spidpay.util.Constant;

import java.text.ParseException;


public class PaymentSuccessfulActivity extends AppCompatActivity {
    ActivityPaymentSuccessfulBinding activityPaymentSuccessfulBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPaymentSuccessfulBinding = ActivityPaymentSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(activityPaymentSuccessfulBinding.getRoot());

        activityPaymentSuccessfulBinding.tvReqestmoney2.setText(getResources().getString(R.string.rupess)+getIntent().getStringExtra("amount"));
        try {
            activityPaymentSuccessfulBinding.tvDate.setText(Constant.convertDate2(getIntent().getStringExtra("datetime")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        activityPaymentSuccessfulBinding.btnBacktohome.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentSuccessfulActivity.this, HostActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        activityPaymentSuccessfulBinding.imgBackpress.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentSuccessfulActivity.this, HostActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

    }
}