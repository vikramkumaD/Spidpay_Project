package com.example.spidpay.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    private static final String PREF_NAME = "spidpay";

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

    public void setIsFirstTime(String isFirstTime) {
        editor.putString("IsFirstTime", isFirstTime);
        editor.commit();
    }

    public String getIsFirstTime() {
        return pref.getString("IsFirstTime", "");
    }

}
