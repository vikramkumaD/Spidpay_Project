package com.example.spidpay.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.RegisterRepository;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.data.response.RegisterResponse;
import com.example.spidpay.databinding.ActivityRegisterBinding;
import com.example.spidpay.interfaces.LoginInterface;
import com.example.spidpay.interfaces.RegisterInterface;
import com.example.spidpay.ui.HostActivity;


public class RegisterActivity extends AppCompatActivity implements RegisterInterface {
    ActivityRegisterBinding activityRegisterBinding;
    RegisterInterface registerInterface;
    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerInterface = (RegisterInterface) this;
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.dashboard_txt3));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.termsncondtion));
        spannableString.setSpan(foregroundColorSpanCyan, 29, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        activityRegisterBinding.tvTermsncondtion.setText(spannableString);

        RegisterRepository registerRepository=new RegisterRepository(RegisterActivity.this,registerInterface);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.registerInterface = registerInterface;
        registerViewModel.registerRepository=registerRepository;
        activityRegisterBinding.setRegisterViewmodel(registerViewModel);
        activityRegisterBinding.setLifecycleOwner(this);
        activityRegisterBinding.edtRegisterfullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.validate_full_name(s.toString());
            }
        });
        activityRegisterBinding.edtRegistermobilenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.validate_mobile_number(s.toString());
            }
        });
    }

    @Override
    public void onServiceStart() {
        activityRegisterBinding.pbLogin.setVisibility(View.VISIBLE);
        registerViewModel.getRegisterResponse();
    }

    @Override
    public void onFailed(String msg) {
        activityRegisterBinding.pbLogin.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSuccess(LiveData<RegisterResponse> responseLiveData) {
        activityRegisterBinding.pbLogin.setVisibility(View.GONE);
        responseLiveData.observe(this, registerResponse -> {
        });
    }
}