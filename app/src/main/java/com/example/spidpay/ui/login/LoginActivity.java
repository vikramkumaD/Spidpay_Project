package com.example.spidpay.ui.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.LoginRepository;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.databinding.ActivityLoginBinding;
import com.example.spidpay.interfaces.ForgotPassInterface;
import com.example.spidpay.interfaces.LoginInterface;

import com.example.spidpay.location.LocationViewModel;
import com.example.spidpay.ui.HostActivity;
import com.example.spidpay.ui.signup.RegisterActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginInterface, ForgotPassInterface {
    LoginViewModel loginViewModel;
    TextView tv_signup;
    BottomSheetDialog forgot_bottomsheet;
    ActivityLoginBinding activityLoginBinding;
    LoginInterface loginInterface;
    int croaselocation, finelocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationViewModel locationViewModel;
    List<String> listPermissionsNeeded;
    final int PERMISSION_REQUEST_CODE = 100;
    ProgressBar pb_forgot;
    ForgotPassInterface forgotPassInterface;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginInterface = this;
        forgotPassInterface = this;
        intliazeView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void intliazeView() {
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.signup_txt));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.seagreen));
        spannableString.setSpan(foregroundColorSpanCyan, 22, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_signup = findViewById(R.id.tv_signup);
        tv_signup.setText(spannableString);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        int fine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        listPermissionsNeeded = new ArrayList<>();
        if (coarse == -1) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (fine == -1) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }


        LoginRepository loginRepository = new LoginRepository(LoginActivity.this, loginInterface,forgotPassInterface);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.loginInterface = loginInterface;
        loginViewModel.forgotPassInterface = forgotPassInterface;
        loginViewModel.loginRepository = loginRepository;

        activityLoginBinding.setLoginViewmodel(loginViewModel);
        activityLoginBinding.setLifecycleOwner(this);

        activityLoginBinding.edtLoginMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.check_mobile_number(s.toString());
            }
        });

        loginViewModel.mstring_mobile_number.observe(this, s -> activityLoginBinding.edtLoginMobileNumber.setText(s));


        activityLoginBinding.tvSignup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        activityLoginBinding.tvLoginforgotpass.setOnClickListener(v -> forgotpasswordDialog());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        croaselocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        finelocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (croaselocation == 0 && finelocation == 0) {
            if (!checkPermission()) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result1 == 0) {
            int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            return result2 == 0;
        } else {
            return false;
        }
    }

    public void forgotpasswordDialog() {
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.forgotpasswordlayout, null);
        forgot_bottomsheet = new BottomSheetDialog(LoginActivity.this);
        forgot_bottomsheet.setContentView(view);
        forgot_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pb_forgot = view.findViewById(R.id.pb_login);

        EditText edt_login_mobile_number = view.findViewById(R.id.edt_login_mobile_number);
        EditText edt_loginpassword = view.findViewById(R.id.edt_loginpassword);
        ImageView img_cleare_forgot_mobilenumber = view.findViewById(R.id.img_cleare_forgot_mobilenumber);
        Button btn_continue = view.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(v -> {
            if (!edt_login_mobile_number.getText().toString().trim().equals("") && !edt_loginpassword.getText().toString().trim().equals("")) {
                loginViewModel.mobile_number = edt_login_mobile_number.getText().toString().trim();
                loginViewModel.password = edt_loginpassword.getText().toString().trim();
                loginViewModel.validate_Forgot_Field(v);

            } else {
                Constant.showToast(LoginActivity.this, getResources().getString(R.string.filedcannotbeblank));
            }
        });
        edt_login_mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    img_cleare_forgot_mobilenumber.setVisibility(View.VISIBLE);
                } else {
                    img_cleare_forgot_mobilenumber.setVisibility(View.GONE);
                }
            }
        });
        forgot_bottomsheet.show();
    }

    @Override
    public void onServiceStart() {
        Constant.dismissKeyboard(LoginActivity.this);
        activityLoginBinding.pbLogin.setVisibility(View.VISIBLE);
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        locationViewModel.getLocationUpdate().observe(this, locationModel -> {
            if (locationModel != null && locationModel.latitude != null && locationModel.longitude != null && locationModel.city != null) {

                locationViewModel.removeLocationUpdate();
                loginViewModel.latitude = String.valueOf(locationModel.latitude);
                loginViewModel.longitude = String.valueOf(locationModel.longitude);
                loginViewModel.city = locationModel.city;
                loginViewModel.getLoginResponse();
            }
        });
    }

    @Override
    public void onFailed(String msg) {
        activityLoginBinding.pbLogin.setVisibility(View.GONE);
        Constant.showToast(LoginActivity.this, msg);
    }

    @Override
    public void onSuccess(LiveData<LoginResponse> responseLiveData) {
        activityLoginBinding.pbLogin.setVisibility(View.GONE);
        responseLiveData.observe(this, loginResponse -> {
            if (loginResponse.status.equals(Constant.Success)) {
                new PrefManager(LoginActivity.this).setUserID(loginResponse.loginUserData.userId);
                startActivity(new Intent(LoginActivity.this, HostActivity.class));
            }
        });
    }

    @Override
    public void onForgotSuccess(LiveData<CommonResponse> commonResponseLiveData) {
        pb_forgot.setVisibility(View.GONE);
        commonResponseLiveData.observe(this, commonResponse -> {
            if (commonResponse.message.equals(Constant.Success)) {
                forgot_bottomsheet.dismiss();
                Constant.showToast(LoginActivity.this, getResources().getString(R.string.passwordreset));
            }
        });
    }

    @Override
    public void onForgotFailed(String msg) {
        pb_forgot.setVisibility(View.GONE);
        Constant.showToast(LoginActivity.this, msg);
    }

    @Override
    public void onForgotStart() {
        pb_forgot.setVisibility(View.VISIBLE);
        loginViewModel.getResetPassword();
    }

}