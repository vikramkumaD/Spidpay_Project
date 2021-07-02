package com.example.spidpay.data;

import android.content.Context;

import com.example.spidpay.util.NetworkInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit retrofitinternal = null;

    public static Retrofit GetRetrofitClient(Context context,String baseurl) {
        retrofitinternal = null;
        HttpLoggingInterceptor logginginternal = new HttpLoggingInterceptor();
        logginginternal.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClientinternal = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new NetworkInterceptor(context))
                .addInterceptor(logginginternal)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
        retrofitinternal = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientinternal)
                .build();

        return retrofitinternal;

    }

}
