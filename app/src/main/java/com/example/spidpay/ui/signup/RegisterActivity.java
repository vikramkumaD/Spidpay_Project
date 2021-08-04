package com.example.spidpay.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.service.autofill.UserData;
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
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.data.response.RegisterResponse;
import com.example.spidpay.data.response.UserInfo;
import com.example.spidpay.databinding.ActivityRegisterBinding;
import com.example.spidpay.db.AppDatabase;
import com.example.spidpay.db.UserDao;
import com.example.spidpay.interfaces.OnStaticClickIterface;
import com.example.spidpay.interfaces.RegisterInterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.ui.login.LoginActivity;
import com.example.spidpay.ui.verifyotp.VerifyOTPActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class RegisterActivity extends AppCompatActivity implements RegisterInterface, OnStaticClickIterface, StaticInterface {
    ActivityRegisterBinding activityRegisterBinding;
    RegisterInterface registerInterface;
    RegisterViewModel registerViewModel;
    BottomSheetDialog interrestedfor_bottomsheet;
    OnStaticClickIterface onStaticClickIterface;
    StaticRepository staticRepository;
    StaticInterface staticInterface;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerInterface = this;
        onStaticClickIterface = this;
        staticInterface = (StaticInterface) this;
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.dashboard_txt3));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.termsncondtion));
        spannableString.setSpan(foregroundColorSpanCyan, 29, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        activityRegisterBinding.tvTermsncondtion.setText(spannableString);
        staticRepository = new StaticRepository(RegisterActivity.this, staticInterface);
        RegisterRepository registerRepository = new RegisterRepository(RegisterActivity.this, registerInterface);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.registerInterface = registerInterface;
        registerViewModel.staticRepository = staticRepository;
        registerViewModel.registerRepository = registerRepository;
        registerViewModel.staticInterface = staticInterface;
        activityRegisterBinding.setRegisterViewmodel(registerViewModel);
        activityRegisterBinding.setLifecycleOwner(this);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
        userDao = db.getUserDao();

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
        Constant.START_TOUCH(RegisterActivity.this);
        activityRegisterBinding.pbLogin.setVisibility(View.VISIBLE);
        registerViewModel.getRegisterResponse();
    }

    @Override
    public void onFailed(String msg) {
        Constant.START_TOUCH(RegisterActivity.this);
        activityRegisterBinding.pbLogin.setVisibility(View.GONE);
        Constant.showToast(RegisterActivity.this, msg);
    }


    @Override
    public void onSuccess(LiveData<RegisterResponse> responseLiveData) {
        responseLiveData.observe(this, registerResponse -> {
            if (registerResponse.username != null && !registerResponse.username.equals("")) {

                new Thread(() -> {
                    UserInfo userData = new UserInfo();
                    userData.accountStatus = registerResponse.accountStatus;
                    userData.domainCode = registerResponse.domainCode;
                    userData.userId = registerResponse.userId;
                    userData.firstName = registerResponse.firstName;
                    userData.lastName = registerResponse.lastName;
                    userData.domainCode = registerResponse.domainCode;
                    userData.parentUserID = registerResponse.parentUser.userId;
                    userData.username = registerResponse.username;
                    userData.userScope = registerResponse.userScope;
                    userData.whiteLabelUserID = registerResponse.whiteLabelUserID;
                    userData.userType = registerResponse.userType;
                    userDao.insertUser(userData);
                    userDao.insertParent(registerResponse.parentUser);
                }).start();


                Constant.START_TOUCH(RegisterActivity.this);
                activityRegisterBinding.pbLogin.setVisibility(View.GONE);
                new PrefManager(RegisterActivity.this).setUserID(registerResponse.userId);
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
            if (interrestedforResponses.size() > 0) {
                Constant.START_TOUCH(RegisterActivity.this);
                activityRegisterBinding.pbLogin.setVisibility(View.GONE);
            }

            InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onStaticClickIterface);
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

    @Override
    public void onStaticStart() {
        Constant.STOP_TOUCH(RegisterActivity.this);
        activityRegisterBinding.pbLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStaticFailed(String msg) {
        Constant.STOP_TOUCH(RegisterActivity.this);
        Constant.showToast(RegisterActivity.this, msg);
        activityRegisterBinding.pbLogin.setVisibility(View.GONE);
    }
}