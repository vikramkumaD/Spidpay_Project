package com.example.spidpay.ui.dmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.spidpay.R;
import com.example.spidpay.databinding.VerifyotpdialogBinding;
import com.example.spidpay.ui.login.LoginActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class RegisterSenderActivity extends AppCompatActivity {

    BottomSheetDialog verifysenderDialog;
    VerifyotpdialogBinding verifyotpdialogBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sender);
    }


    public void verifySenderOTP()
    {

        verifysenderDialog = new BottomSheetDialog(RegisterSenderActivity.this);
        verifyotpdialogBinding = DataBindingUtil.inflate(LayoutInflater.from(RegisterSenderActivity.this), R.layout.verfiysenderdialog, null, false);
        verifysenderDialog.setContentView(verifyotpdialogBinding.getRoot());
        verifysenderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        verifysenderDialog.show();


    }
}