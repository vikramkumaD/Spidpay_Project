package com.example.spidpay.util;

import androidx.databinding.InverseMethod;

public class Converter {

    public static int convertStringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @InverseMethod("convertStringToInt")
    public static String convertIntToString(int value) {
        return String.valueOf(value);
    }
}
