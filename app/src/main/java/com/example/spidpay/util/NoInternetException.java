package com.example.spidpay.util;

import java.io.IOException;

public class NoInternetException extends IOException {
    public String getMessage() {
        return "No connectivity exception";
    }
}
