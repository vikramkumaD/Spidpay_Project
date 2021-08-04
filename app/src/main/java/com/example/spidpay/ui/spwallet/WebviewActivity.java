package com.example.spidpay.ui.spwallet;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.spidpay.R;
import com.example.spidpay.databinding.ActivityWebviewBinding;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;

public class WebviewActivity extends AppCompatActivity {
    ActivityWebviewBinding activityWebviewBinding;
    private String Url = "http://test.wlt.spidpay.in/spidpay-wallet#/wlt-online-payment?userId=";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWebviewBinding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(activityWebviewBinding.getRoot());
        activityWebviewBinding.view.getSettings().setJavaScriptEnabled(true);
        activityWebviewBinding.view.getSettings().setBuiltInZoomControls(true);
        Url = Url + new PrefManager(WebviewActivity.this).getUserID() + "&walletId=" + getIntent().getStringExtra("walletId");
        activityWebviewBinding.view.loadUrl(Url);
        activityWebviewBinding.view.canGoBack();
        activityWebviewBinding.imgBackpress.setOnClickListener(v -> finish());
        activityWebviewBinding.view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                activityWebviewBinding.view.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("ok", url);
                if (url.contains(Constant.ONLINE_SUCCESS) || url.contains(Constant.ONLINE_FAILED)) {

                    if (url.contains(Constant.ONLINE_SUCCESS)) {
                        Intent intent = new Intent(WebviewActivity.this, PaymentSuccessfulActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(WebviewActivity.this, PaymentFailedActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
                super.onReceivedLoginRequest(view, realm, account, args);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                //Log.e("error", String.valueOf(error.getPrimaryError()));
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                // Log.e("error", String.valueOf(errorResponse.getStatusCode()));
            }
        });
    }
}