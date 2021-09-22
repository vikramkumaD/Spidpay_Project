package com.example.spidpay.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.databinding.DataBindingUtil;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.spidpay.R;
import com.example.spidpay.databinding.SplashscreenBinding;
import com.example.spidpay.ui.login.LoginActivity;
import com.example.spidpay.util.PrefManager;
import java.util.concurrent.Executor;

public class SplashscreenActivity extends AppCompatActivity {
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SplashscreenBinding splashscreenBinding = DataBindingUtil.setContentView(this, R.layout.splashscreen);
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.splashtxt));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.seagreen));
        ForegroundColorSpan foregroundColorSpanBlue = new ForegroundColorSpan(getResources().getColor(R.color.seagreen));
        spannableString.setSpan(foregroundColorSpanCyan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColorSpanBlue, 31, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        splashscreenBinding.tvSplash.setText(spannableString);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(SplashscreenActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(SplashscreenActivity.this, HostActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for Spidpay app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                if (new PrefManager(SplashscreenActivity.this).getIsFirstTime()) {
                    if (new PrefManager(SplashscreenActivity.this).getIsLandingPageOpen()) {
                        if (isAppHasSecurity()) {

                            if(isFingerprintAvailable())
                            {
                                runOnUiThread(() -> biometricPrompt.authenticate(promptInfo));
                            }
                            else {
                                openPinOrPatternScreen();
                            }

                        } else {
                            startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
                            finish();
                        }
                    } else {
                        startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
                        finish();
                    }

                } else {
                    startActivity(new Intent(SplashscreenActivity.this, WelcomeActivity.class));
                    finish();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public boolean isAppHasSecurity() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.isKeyguardSecure();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public Boolean isFingerprintAvailable()
    {
        FingerprintManager fingerprintManager=(FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        if (!fingerprintManager.isHardwareDetected()) {
            // Device doesn't support fingerprint authentication
            return  false;
        } else if (!fingerprintManager.hasEnrolledFingerprints()) {
            // User hasn't enrolled any fingerprints to authenticate with
            return false;
        } else {
            // Everything is ready for fingerprint authentication
            return  true;
        }

    }


    public void openPinOrPatternScreen()
    {
        KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        if (km.isKeyguardSecure()) {
            Intent authIntent = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
            }
            startActivityForResult(authIntent, 100);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(SplashscreenActivity.this, HostActivity.class));
                finish();
            }
        }
    }
}