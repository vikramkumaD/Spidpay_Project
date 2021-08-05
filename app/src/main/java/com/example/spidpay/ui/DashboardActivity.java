package com.example.spidpay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.spidpay.R;
import com.example.spidpay.ui.login.LoginActivity;
import com.example.spidpay.ui.signup.RegisterActivity;
import com.example.spidpay.util.PrefManager;

public class DashboardActivity extends AppCompatActivity {
    TextView tv_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);



        findViewById(R.id.btn_dashboardlogin).setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, LoginActivity.class)));
        tv_signup = findViewById(R.id.tv_signup);
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.signup_txt));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.seagreen));
        spannableString.setSpan(foregroundColorSpanCyan, 22, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_signup.setText(spannableString);

        TextView tv_termsncondtion = findViewById(R.id.tv_termsncondtion);
        SpannableString spannableStringterms = new SpannableString(getResources().getString(R.string.dashboard_txt3));
        ForegroundColorSpan foregroundColorSpanCyanterms = new ForegroundColorSpan(getResources().getColor(R.color.termsncondtion));
        spannableStringterms.setSpan(foregroundColorSpanCyanterms, 29, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_termsncondtion.setText(spannableStringterms);

        tv_signup.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, RegisterActivity.class)));

    }
}