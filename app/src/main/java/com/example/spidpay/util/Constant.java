package com.example.spidpay.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant {
/*
    http://13.232.183.230:6700/spidpay-wallet/swagger-ui/
    http://3.108.193.164:6600/spidpay-txnactivity/swagger-ui/
    http://65.2.129.146:6500/spidpay-identity/swagger-ui/*/


    //http://3.108.193.164:6600/spidpay-txnactivity/v1
    //http://13.232.183.230:6700/spidpay-wallet/v1/trade/payout


    public static final String USER_API = "http://65.2.129.146:6500/spidpay-identity/api/";
    public static final String WALLET_API = "http://13.232.183.230:6700/spidpay-wallet/";
    public static final String TRANSACTION_API = "http://3.108.193.164:6600/spidpay-txnactivity";


    public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{9,20})";

    public static final String Server_ERROR = "Server error!";
    public static final String BANK = "bank";
    public static final String COMPANY = "company";
    public static final String USER = "user";
    public static final String ROLE_INTERRESTEDFOR = "SP_EXT_WHITE_LABLE";
    public static final String NO_INTERNET = "no internet";
    public static final String CREDIT = "CREDIT";
    public static final String Success = "Success";
    public static final String DOMAIN_NAME="spidtail";
    public static final String PARENT_ID="eaf02719-c928-42d5-86a8-7530933a44ca";

    public static final int BOTTOM_HOME = 1;
    public static final int BOTTOM_COMMISION = 2;
    public static final int BOTTOM_NOTIFICATION = 3;
    public static final int BOTTOM_HISTORY = 4;


    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{9,20}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public static boolean isValidIFSCode(String str) {
        String regex = "^[A-Z]{4}0[A-Z0-9]{6}$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isValidGSTNo(String str) {
        String regex = "^[0-9]{2}[A-Z]{5}[0-9]{4}" + "[A-Z]{1}[1-9A-Z]{1}" + "Z[0-9A-Z]{1}$";

        Pattern p = Pattern.compile(regex);

        if (str == null) {
            return false;
        }

        Matcher m = p.matcher(str);

        return m.matches();
    }

    public static boolean isValidPanCardNo(String panCardNo) {
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        Pattern p = Pattern.compile(regex);
        if (panCardNo == null) {
            return false;
        }
        Matcher m = p.matcher(panCardNo);
        return m.matches();
    }


    public static boolean isValidAadharNumber(String str) {
        final String pattern = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$";
        Pattern p = Pattern.compile(pattern);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String parseErrorBodyofRetrofit(String errorbody) {
        JSONObject jsonObject1;
        try {
            jsonObject1 = new JSONObject(errorbody);
            JSONObject jsonObject = jsonObject1.getJSONObject("error");
            return jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
            return e.toString();
        }
    }


    public static void STOP_TOUCH(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void START_TOUCH(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
    }

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
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

    public static String convertDate(String Date) throws ParseException {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("MMMM dd yyyy, hh:mm:ss a", Locale.ENGLISH);
        java.util.Date date = inputFormat.parse(Date);
        assert date != null;
        return outputFormat.format(date);
    }
}
