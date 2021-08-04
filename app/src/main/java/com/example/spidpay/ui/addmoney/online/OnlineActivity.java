package com.example.spidpay.ui.addmoney.online;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.spidpay.databinding.ActivityOnlineBinding;
import com.example.spidpay.ui.addmoney.AddMoneyViewModel;

public class OnlineActivity extends AppCompatActivity {
    ActivityOnlineBinding activityOnlineBinding;
    AddMoneyViewModel addMoneyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOnlineBinding = ActivityOnlineBinding.inflate(getLayoutInflater());
        setContentView(activityOnlineBinding.getRoot());

        addMoneyViewModel = new ViewModelProvider(this).get(AddMoneyViewModel.class);
        activityOnlineBinding.setAddmoneyviewmodel(addMoneyViewModel);
        activityOnlineBinding.executePendingBindings();

        addMoneyViewModel.online_money.observe(this, s -> {
                    activityOnlineBinding.edtOnlineAmount.setText(s);
        });

    }


}