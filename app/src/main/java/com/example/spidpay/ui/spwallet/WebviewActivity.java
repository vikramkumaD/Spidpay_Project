package com.example.spidpay.ui.spwallet;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.example.spidpay.util.PrefManager;

public class WebviewActivity extends AppCompatActivity {
    ActivityWebviewBinding activityWebviewBinding;
    private String Url = "http://test.wlt.spidpay.in/spidpay-wallet#/wlt-online-payment?userId=";
    private String tempurl="http://test.wlt.spidpay.in/spidpay-wallet#/wlt-online-payment?userId=eaf02719-c928-42d5-86a8-7530933a44ca&walletId=1106621477978039";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWebviewBinding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(activityWebviewBinding.getRoot());
        activityWebviewBinding.view.getSettings().setJavaScriptEnabled(true);
        activityWebviewBinding.view.getSettings().setBuiltInZoomControls(true);
        Url = Url+new PrefManager(WebviewActivity.this).getUserID() + "&walletId="+ getIntent().getStringExtra("walletId");
        activityWebviewBinding.view.loadUrl(tempurl);
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
                Log.e("error", String.valueOf(error.getPrimaryError()));
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.e("error", String.valueOf(errorResponse.getStatusCode()));
            }
        });
    }
}