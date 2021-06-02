package com.example.spidpay.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.WindowManager;

import com.example.spidpay.R;
import com.example.spidpay.databinding.SplashscreenBinding;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SplashscreenBinding splashscreenBinding= DataBindingUtil.setContentView(this,R.layout.splashscreen);
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.splashtxt));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.seagreen));
        ForegroundColorSpan foregroundColorSpanBlue = new ForegroundColorSpan(getResources().getColor(R.color.seagreen));
        ForegroundColorSpan foregroundColorSpanGreen = new ForegroundColorSpan(Color.GREEN);
        spannableString.setSpan(foregroundColorSpanCyan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColorSpanBlue, 31, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        splashscreenBinding.tvSplash.setText(spannableString);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                startActivity(new Intent(SplashscreenActivity.this, WelcomeActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}