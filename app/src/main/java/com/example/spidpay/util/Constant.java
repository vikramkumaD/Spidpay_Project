package com.example.spidpay.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Constant {

    public static final String USER_API = "http://45.79.120.79:6500/spidpay-identity/api/";

    public static final String Success = "Success";

    public static final int BOTTOM_HOME = 1;
    public static final int BOTTOM_COMMISION = 2;
    public static final int BOTTOM_NOTIFICATION = 3;
    public static final int BOTTOM_HISTORY = 4;


    public static void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
    }

    public static void showToast(Context context,String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
