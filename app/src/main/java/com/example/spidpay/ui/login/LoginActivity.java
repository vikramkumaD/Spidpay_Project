package com.example.spidpay.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.LoginRepository;
import com.example.spidpay.data.response.BooleanResponse;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.data.response.LoginResponse;
import com.example.spidpay.data.response.VerifyOTPResponse;
import com.example.spidpay.databinding.ActivityLoginBinding;
import com.example.spidpay.databinding.ForgotpasswordlayoutBinding;
import com.example.spidpay.databinding.VerifyotpdialogBinding;
import com.example.spidpay.databinding.VerifyusernameBinding;
import com.example.spidpay.db.AppDatabase;
import com.example.spidpay.db.UserDao;
import com.example.spidpay.interfaces.ForgotPassInterface;
import com.example.spidpay.interfaces.LoginInterface;

import com.example.spidpay.location.GpsUtils;
import com.example.spidpay.ui.DashboardActivity;
import com.example.spidpay.ui.signup.RegisterActivity;
import com.example.spidpay.ui.verifyotp.VerifyOTPActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity implements LoginInterface, ForgotPassInterface {
    LoginViewModel loginViewModel;
    TextView tv_signup;
    BottomSheetDialog forgot_bottomsheet;
    ActivityLoginBinding activityLoginBinding;
    LoginInterface loginInterface;
    ForgotPassInterface forgotPassInterface;
    BottomSheetDialog verify_username_bottomsheet, verify_OTP_bottomsheet;
    VerifyusernameBinding verifyusernameBinding;
    VerifyotpdialogBinding verifyOtpDialogBinding;
    ForgotpasswordlayoutBinding forgotpasswordlayoutBinding;
    private int forgot_dialog_counter = 0;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    UserDao userDao;

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
        new PrefManager(LoginActivity.this).setIsFirstTime(true);
        SpannableString signupstr = new SpannableString(getResources().getString(R.string.signup_txt));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.dark_blue_extra));
        signupstr.setSpan(foregroundColorSpanCyan, 22, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_signup = findViewById(R.id.tv_signup);
        tv_signup.setText(signupstr);

        SpannableString termnconditionstr = new SpannableString(getResources().getString(R.string.dashboard_txt3));
        ForegroundColorSpan foregroundColorSpanCyan2 = new ForegroundColorSpan(getResources().getColor(R.color.forgot_password_Txtcolor));
        termnconditionstr.setSpan(foregroundColorSpanCyan2, 29, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        activityLoginBinding.tvTermsncondtion.setText(termnconditionstr);


        LoginRepository loginRepository = new LoginRepository(LoginActivity.this, loginInterface, forgotPassInterface);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.loginInterface = loginInterface;
        loginViewModel.forgotPassInterface = forgotPassInterface;
        loginViewModel.loginRepository = loginRepository;

        activityLoginBinding.setLoginViewmodel(loginViewModel);
        activityLoginBinding.setLifecycleOwner(this);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
        userDao = db.getUserDao();


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
        activityLoginBinding.tvLoginforgotpass.setOnClickListener(v -> verifyUserNameDialog());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NotNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        loginViewModel.latitude = String.valueOf(location.getLatitude());
                        loginViewModel.longitude = String.valueOf(location.getLongitude());
                        loginViewModel.city = "";
                        loginViewModel.getLoginResponse();
                    }
                }
            }
        };
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(LoginActivity.this, location -> {
                    if (location != null) {
                        getLocation();
                    } else {
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void forgotpasswordDialog() {
        forgot_dialog_counter = 3;
        forgot_bottomsheet = new BottomSheetDialog(LoginActivity.this);
        forgotpasswordlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(LoginActivity.this), R.layout.forgotpasswordlayout, null, false);
        forgot_bottomsheet.setContentView(forgotpasswordlayoutBinding.getRoot());
        forgot_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        forgotpasswordlayoutBinding.setLoginviewmodel(loginViewModel);
        forgotpasswordlayoutBinding.setLifecycleOwner(this);
        forgot_bottomsheet.show();

    }

    public void verifyUserNameDialog() {
        forgot_dialog_counter = 1;
        verify_username_bottomsheet = new BottomSheetDialog(LoginActivity.this);
        verifyusernameBinding = DataBindingUtil.inflate(LayoutInflater.from(LoginActivity.this), R.layout.verifyusername, null, false);
        verify_username_bottomsheet.setContentView(verifyusernameBinding.getRoot());
        verifyusernameBinding.setLoginviewmodel(loginViewModel);
        verifyusernameBinding.setLifecycleOwner(this);
        verify_username_bottomsheet.show();
    }

    public void verifyOTPDialog() {
        forgot_dialog_counter = 2;
        verify_OTP_bottomsheet = new BottomSheetDialog(LoginActivity.this);
        verifyOtpDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(LoginActivity.this), R.layout.verifyotpdialog, null, false);
        verify_OTP_bottomsheet.setContentView(verifyOtpDialogBinding.getRoot());
        verifyOtpDialogBinding.setLoginviewmodel(loginViewModel);
        verifyOtpDialogBinding.setLifecycleOwner(this);
        verify_OTP_bottomsheet.show();
    }

    @Override
    public void onServiceStart() {
        Constant.STOP_TOUCH(LoginActivity.this);
        Constant.dismissKeyboard(LoginActivity.this);
        activityLoginBinding.pbLogin.setVisibility(View.VISIBLE);

        new GpsUtils(this).turnGPSOn(isGPSEnable -> {
            if (isGPSEnable) {
                getLocation();
            } else {
                Constant.showToast(LoginActivity.this, getResources().getString(R.string.turnongps));
            }
        });
    }

    @Override
    public void onFailed(String msg) {
        Constant.START_TOUCH(LoginActivity.this);
        activityLoginBinding.pbLogin.setVisibility(View.GONE);
        Constant.showToast(LoginActivity.this, msg);
    }

    @Override
    public void onSuccess(LiveData<LoginResponse> responseLiveData) {
        responseLiveData.observe(this, loginResponse -> {
            if (loginResponse.status.equals(Constant.Success)) {

                new Thread(() -> {
                    userDao.insertUser(loginResponse.loginUserInfo);
                    userDao.insertParent(loginResponse.loginUserInfo.parentUser);
                }).start();

                if (loginResponse.loginUserInfo.accountStatus.equals(Constant.ACCOUNT_STATUS))
                    new PrefManager(LoginActivity.this).setKYCPending(false);
                else
                    new PrefManager(LoginActivity.this).setKYCPending(true);


                Constant.START_TOUCH(LoginActivity.this);
                activityLoginBinding.pbLogin.setVisibility(View.GONE);
                new PrefManager(LoginActivity.this).setUserID(loginResponse.loginUserInfo.userId);
                Intent intent = new Intent(LoginActivity.this, VerifyOTPActivity.class);
                intent.putExtra("username", loginResponse.loginUserInfo.username);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onForgotVerifySuccess(LiveData<BooleanResponse> commonResponseLiveData) {
        commonResponseLiveData.observe(this, commonResponse -> {
            if (commonResponse.status) {
                Constant.START_TOUCH(LoginActivity.this);
                verifyusernameBinding.pbForgot.setVisibility(View.GONE);
                verify_username_bottomsheet.dismiss();
                verifyOTPDialog();
            }
        });
    }

    @Override
    public void onForgotResetSuccess(LiveData<CommonResponse> commonResponseLiveData) {
        commonResponseLiveData.observe(this, commonResponse -> {
            forgot_bottomsheet.dismiss();
            Constant.START_TOUCH(LoginActivity.this);
            forgotpasswordlayoutBinding.pbForgot.setVisibility(View.GONE);
            Constant.showToast(LoginActivity.this, commonResponse.message);
        });
    }

    @Override
    public void onVerifyOTPSuccess(LiveData<VerifyOTPResponse> commonResponseLiveData) {
        commonResponseLiveData.observe(this, verifyOTPResponse -> {
            Constant.START_TOUCH(LoginActivity.this);
            verify_OTP_bottomsheet.dismiss();
            verifyOtpDialogBinding.pbForgot.setVisibility(View.GONE);
            forgotpasswordDialog();
        });
    }

    @Override
    public void onForgotFailed(String msg) {
        Constant.START_TOUCH(LoginActivity.this);
        Constant.showToast(LoginActivity.this, msg);
        if (forgot_dialog_counter == 1) {
            verifyusernameBinding.pbForgot.setVisibility(View.GONE);
        } else if (forgot_dialog_counter == 2) {
            verifyOtpDialogBinding.pbForgot.setVisibility(View.GONE);
        } else if (forgot_dialog_counter == 3) {
            forgotpasswordlayoutBinding.pbForgot.setVisibility(View.GONE);
        }
    }

    @Override
    public void onForgotStart() {
        Constant.STOP_TOUCH(LoginActivity.this);
        if (forgot_dialog_counter == 1) {
            verifyusernameBinding.pbForgot.setVisibility(View.VISIBLE);
            loginViewModel.verifyUserName();
        } else if (forgot_dialog_counter == 2) {
            verifyOtpDialogBinding.pbForgot.setVisibility(View.VISIBLE);
            loginViewModel.getVerifyOTP();
        } else if (forgot_dialog_counter == 3) {
            forgotpasswordlayoutBinding.pbForgot.setVisibility(View.VISIBLE);
            loginViewModel.getResetPassword();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stoplocationUpdate();
    }


    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NotNull LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                loginViewModel.latitude = String.valueOf(location.getLatitude());
                loginViewModel.longitude = String.valueOf(location.getLongitude());
                loginViewModel.city = "";
                loginViewModel.getLoginResponse();

            }
        }
    };


    public void stoplocationUpdate() {
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

   /* public void getAddress(Location location) {
        Geocoder geocoder;
        String city = "";
        List<Address> addresses;
        geocoder = new Geocoder(LoginActivity.this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            city = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
            getLocation();
        }
        loginViewModel.latitude = String.valueOf(location.getLatitude());
        loginViewModel.longitude = String.valueOf(location.getLongitude());
        loginViewModel.city = city;
        loginViewModel.getLoginResponse();

    }*/

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        } else {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(LoginActivity.this, location -> {
                if (location != null) {
                    loginViewModel.latitude = String.valueOf(location.getLatitude());
                    loginViewModel.longitude = String.valueOf(location.getLongitude());
                    loginViewModel.city = "";
                    loginViewModel.getLoginResponse();
                } else {
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                }
            });

        }
    }

}