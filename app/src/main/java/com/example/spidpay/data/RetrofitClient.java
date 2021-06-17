package com.example.spidpay.data;

import android.content.Context;

import com.example.spidpay.util.Constant;
import com.example.spidpay.util.NetworkInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit retrofitinternal = null;

    public static Retrofit GetRetrofitClient( Context context) {
        retrofitinternal = null;
        HttpLoggingInterceptor logginginternal = new HttpLoggingInterceptor();
        logginginternal.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClientinternal = new OkHttpClient.Builder()
                .readTimeout(360, TimeUnit.SECONDS)
                .addInterceptor(new NetworkInterceptor(context))
                .addInterceptor(logginginternal)
                .connectTimeout(360, TimeUnit.SECONDS)
                .build();
        retrofitinternal = new Retrofit.Builder()
                .baseUrl(Constant.USER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientinternal)
                .build();

        return retrofitinternal;

    }

}