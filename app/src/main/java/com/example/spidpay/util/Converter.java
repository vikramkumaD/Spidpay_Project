package com.example.spidpay.util;

import androidx.databinding.InverseMethod;

public class Converter {

    public static Double convertStringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @InverseMethod("convertStringToDouble")
    public static String convertDoubleToString(Double value) {
        return String.valueOf(value);
    }
}
