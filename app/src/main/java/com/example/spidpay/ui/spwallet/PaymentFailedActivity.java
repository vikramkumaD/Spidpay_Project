package com.example.spidpay.ui.spwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.spidpay.R;
import com.example.spidpay.databinding.ActivityPaymentFailedBinding;
import com.example.spidpay.databinding.ActivityTranferMoneyBinding;
import com.example.spidpay.ui.HostActivity;

public class PaymentFailedActivity extends AppCompatActivity {

    ActivityPaymentFailedBinding activityPaymentFailedBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPaymentFailedBinding = ActivityPaymentFailedBinding.inflate(getLayoutInflater());
        setContentView(activityPaymentFailedBinding.getRoot());

        activityPaymentFailedBinding.imgBackpress.setOnClickListener(v -> finish());

        activityPaymentFailedBinding.btnBacktohome.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentFailedActivity.this, HostActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}