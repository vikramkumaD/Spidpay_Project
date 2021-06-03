package com.example.spidpay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.spidpay.R;
import com.example.spidpay.ui.signup.RegisterActivity;

public class VerifyOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_o_t_p);
        findViewById(R.id.btn_verify_otp).setOnClickListener(v -> startActivity(new Intent(VerifyOTPActivity.this, RegisterActivity.class)));
    }
}