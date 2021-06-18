package com.example.spidpay.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant {

    public static final String USER_API = "http://45.79.120.79:6500/spidpay-identity/api/";

    //http://45.79.120.79:6500/spidpay-identity/api/user/v1/update/address



    public static final String USER="user";
    public static final String ROLE_INTERRESTEDFOR="SP_EXT_WHITE_LABLE";

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

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static String firstLetterUppdaerCase(String str) {

        // Create a char array of given String
        char[] ch = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {

            // If first character of a word is found
            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {

                // If it is in lower-case
                if (ch[i] >= 'a' && ch[i] <= 'z') {

                    // Convert into Upper-case
                    ch[i] = (char) (ch[i] - 'a' + 'A');
                }
            }

            // If apart from first character
            // Any one is in Upper-case
            else if (ch[i] >= 'A' && ch[i] <= 'Z')

                // Convert into Lower-Case
                ch[i] = (char) (ch[i] + 'a' - 'A');
        }

        // Convert the char array to equivalent String
        return new String(ch);
    }
}
