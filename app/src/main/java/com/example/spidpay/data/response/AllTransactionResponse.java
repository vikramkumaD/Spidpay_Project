package com.example.spidpay.data.response;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.spidpay.R;
import com.example.spidpay.util.Constant;

import java.text.ParseException;

public class AllTransactionResponse {
    public String domain, transactionId, walletId, walletType, amount, transactionType, transactionCategory, updatedBy, status, notes, creationTime, updateTime;

    public String retrunDate(String date) {
        try {
            return Constant.convertDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
