package com.example.spidpay.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.RegisterRepository;
import com.example.spidpay.data.response.RegisterResponse;
import com.example.spidpay.databinding.ActivityRegisterBinding;
import com.example.spidpay.interfaces.OnClickIterface;
import com.example.spidpay.interfaces.RegisterInterface;
import com.example.spidpay.ui.verifyotp.VerifyOTPActivity;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class RegisterActivity extends AppCompatActivity implements RegisterInterface, OnClickIterface {
    ActivityRegisterBinding activityRegisterBinding;
    RegisterInterface registerInterface;
    RegisterViewModel registerViewModel;
    BottomSheetDialog interrestedfor_bottomsheet;
    OnClickIterface onClickIterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerInterface = this;
        onClickIterface = this;
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.dashboard_txt3));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.termsncondtion));
        spannableString.setSpan(foregroundColorSpanCyan, 29, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        activityRegisterBinding.tvTermsncondtion.setText(spannableString);
        RegisterRepository registerRepository = new RegisterRepository(RegisterActivity.this, registerInterface);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.registerInterface = registerInterface;
        registerViewModel.registerRepository = registerRepository;
        activityRegisterBinding.setRegisterViewmodel(registerViewModel);
        activityRegisterBinding.setLifecycleOwner(this);
        activityRegisterBinding.edtRegisterIntrestedFor.setOnClickListener(v -> showInterestedForDialog());
        activityRegisterBinding.edtRegisterFirstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.update_first_name(s.toString());
            }
        });
        activityRegisterBinding.edtRegisterLastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.update_last_name(s.toString());
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
                registerViewModel.update_mobile_number(s.toString());
            }
        });
        activityRegisterBinding.edtRegisterEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.update_email_Address(s.toString());
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
            if (registerResponse.username != null && !registerResponse.username.equals("")) {
                Intent intent = new Intent(RegisterActivity.this, VerifyOTPActivity.class);
                intent.putExtra("username", registerResponse.username);
                startActivity(intent);
                finish();
            }
        });
    }

    public void showInterestedForDialog() {
        View view = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.interrestedfor_bottomsheet, null);
        interrestedfor_bottomsheet = new BottomSheetDialog(RegisterActivity.this);
        interrestedfor_bottomsheet.setContentView(view);
        interrestedfor_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView rv_interreseted_for = view.findViewById(R.id.rv_interreseted_for);
        rv_interreseted_for.setLayoutManager(new LinearLayoutManager(RegisterActivity.this));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(RegisterActivity.this, R.dimen.marign10dp);
        rv_interreseted_for.addItemDecoration(itemOffsetDecoration);

        registerViewModel.getInterrestedFor().observe(this, interrestedforResponses -> {
            InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onClickIterface);
            rv_interreseted_for.setAdapter(interrestedforAdapter);
            interrestedfor_bottomsheet.show();
        });

    }

    @Override
    public void onItemClick(String code, String description) {
        activityRegisterBinding.edtRegisterIntrestedFor.setText(description);
        registerViewModel.code = code;
        interrestedfor_bottomsheet.dismiss();
    }

}