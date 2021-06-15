package com.example.spidpay.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {
    private final Context mContext;
    public NetworkInterceptor(Context context) {
        this.mContext = context;
    }
    @NotNull
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        if (!isInternetAvailable(mContext)) {
            throw new NoInternetException();
        }
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        if (network != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            if (networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return true;
            assert networkCapabilities != null;
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        } else {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo == null) {
                return false;
            }
            return networkInfo.isConnected();
        }
    }
}
