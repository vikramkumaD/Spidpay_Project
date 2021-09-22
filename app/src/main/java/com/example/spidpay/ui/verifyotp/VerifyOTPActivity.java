package com.example.spidpay.ui.verifyotp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.VerifyOTPRepository;
import com.example.spidpay.data.response.VerifyOTPResponse;
import com.example.spidpay.databinding.ActivityVerifyOTPBinding;
import com.example.spidpay.interfaces.VerifyOTPInterface;
import com.example.spidpay.ui.HostActivity;
import com.example.spidpay.util.Constant;

public class VerifyOTPActivity extends AppCompatActivity implements VerifyOTPInterface {

    VerifyOTPInterface verifyOTPInterface;
    ActivityVerifyOTPBinding activityLoginBinding;
    VerifyOTPViewModel verifyOTPViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_verify_o_t_p);
        verifyOTPInterface = this;
        VerifyOTPRepository verifyOTPRepository = new VerifyOTPRepository(VerifyOTPActivity.this, verifyOTPInterface);
        verifyOTPViewModel = new ViewModelProvider(this).get(VerifyOTPViewModel.class);
        verifyOTPViewModel.verifyOTPRepository = verifyOTPRepository;
        verifyOTPViewModel.username = getIntent().getStringExtra("username");
        verifyOTPViewModel.verifyOTPInterface = verifyOTPInterface;
        activityLoginBinding.setVerifyOtp(verifyOTPViewModel);
        activityLoginBinding.setLifecycleOwner(this);
    }


    @Override
    public void onServiceStart() {
        activityLoginBinding.pbVerifyOtp.setVisibility(View.VISIBLE);
        verifyOTPViewModel.getVerifyOTP();
    }

    @Override
    public void onFailed(String msg) {
        Constant.showToast(VerifyOTPActivity.this, msg);
        activityLoginBinding.pbVerifyOtp.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(LiveData<VerifyOTPResponse> verifyOTPResponseLiveData) {

        verifyOTPResponseLiveData.observe(this, verifyOTPResponse -> {
            if (verifyOTPResponse.mobileOTPStatus) {
                activityLoginBinding.pbVerifyOtp.setVisibility(View.GONE);
                startActivity(new Intent(VerifyOTPActivity.this, HostActivity.class));
                finish();
            }
        });
    }

}