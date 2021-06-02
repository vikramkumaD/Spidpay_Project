package com.example.spidpay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.spidpay.R;

public class RegisterActivity extends AppCompatActivity {
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        findViewById(R.id.btn_register).setOnClickListener((View v) -> startActivity(new Intent(RegisterActivity.this, HostActivity.class)));
        TextView tv_termsncondtion=findViewById(R.id.tv_termsncondtion);
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.dashboard_txt3));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.termsncondtion));
        spannableString.setSpan(foregroundColorSpanCyan, 29, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_termsncondtion.setText(spannableString);

    }
}