package com.example.spidpay.ui.tradewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import com.example.spidpay.databinding.ActivityTranferMoneyBinding;
import com.example.spidpay.ui.spwallet.AddMoneyViewModel;


public class TransferMoneyActivity extends AppCompatActivity {
    ActivityTranferMoneyBinding activityTranferMoneyBinding;
    AddMoneyViewModel addMoneyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTranferMoneyBinding = ActivityTranferMoneyBinding.inflate(getLayoutInflater());
        setContentView(activityTranferMoneyBinding.getRoot());
        addMoneyViewModel = new ViewModelProvider(this).get(AddMoneyViewModel.class);
        activityTranferMoneyBinding.setAddmoneyviewmodel(addMoneyViewModel);
        activityTranferMoneyBinding.setLifecycleOwner(this);

        activityTranferMoneyBinding.imgBackpress.setOnClickListener(v -> finish());
    }
}