package com.example.spidpay.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    private static final String PREF_NAME = "spidpay";
    private static final String IS_KYC_PENDING = "kyc_pending";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    public void setUserID(String isFirstTime) {
        editor.putString("userid", isFirstTime);
        editor.commit();
    }

    public String getUserID() {
        return pref.getString("userid", "");
    }


    public void setKYCPending(Boolean isFirstTime) {
        editor.putBoolean(IS_KYC_PENDING, isFirstTime);
        editor.commit();
    }

    public Boolean getKYCPending() {
        return pref.getBoolean(IS_KYC_PENDING, false);
    }



    public void setIsFirstTime(boolean kyc) {
        editor.putBoolean("IsFirstTime", kyc);
        editor.commit();
    }

    public boolean getIsFirstTime() {
        return pref.getBoolean("IsFirstTime", false);
    }


    public void setIsLandingPageOpen(boolean isFirstTime) {
        editor.putBoolean("Islanding", isFirstTime);
        editor.commit();
    }

    public boolean getIsLandingPageOpen() {
        return pref.getBoolean("Islanding", false);
    }

}
