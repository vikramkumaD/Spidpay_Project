package com.example.spidpay.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.spidpay.R;
import com.example.spidpay.databinding.ActivityLoginBinding;
import com.example.spidpay.interfaces.CommonInterface;
import com.example.spidpay.ui.VerifyOTPActivity;

public class LoginActivity extends AppCompatActivity implements CommonInterface {
    LoginViewModel loginViewModel;
    TextView tv_signup;
    ActivityLoginBinding activityLoginBinding;
    CommonInterface commonInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        commonInterface = this;
        intliazeView();

    }

    public void intliazeView() {
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.signup_txt));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.seagreen));
        spannableString.setSpan(foregroundColorSpanCyan, 22, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_signup = findViewById(R.id.tv_signup);
        tv_signup.setText(spannableString);
        findViewById(R.id.btn_login).setOnClickListener((View v) -> startActivity(new Intent(LoginActivity.this, VerifyOTPActivity.class)));
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.commonInterface = commonInterface;
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

        loginViewModel.mstring_mobile_number.observe(this, s -> {
                activityLoginBinding.edtLoginMobileNumber.setText(s);
        });

    }


   /* private void openForgotPassDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this);
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.forgotpasswordlayout, null);
        builder.setView(view);
        android.app.AlertDialog forgotdialog = builder.create();
        RelativeLayout relative_cancel = view.findViewById(R.id.relative_cancel);
        relative_cancel.setOnClickListener(v1 -> forgotdialog.dismiss());


        RelativeLayout relative_ok = view.findViewById(R.id.relative_ok);
        relative_ok.setOnClickListener(v12 -> forgotdialog.dismiss());

        forgotdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        forgotdialog.show();
        forgotdialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(forgotdialog.getWindow().getAttributes());
        int horizontalMargin = (int) getResources().getDimension(R.dimen.marign10dp);
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin = horizontalMargin;
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin = horizontalMargin;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        forgotdialog.getWindow().setAttributes(layoutParams);

    }*/

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(LoginActivity.this,VerifyOTPActivity.class));
    }
}