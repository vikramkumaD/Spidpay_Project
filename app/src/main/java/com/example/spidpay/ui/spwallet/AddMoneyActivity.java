package com.example.spidpay.ui.spwallet;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.spidpay.databinding.ActivityAddMoneyBinding;
import com.example.spidpay.interfaces.CommonInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;

public class AddMoneyActivity extends AppCompatActivity implements CommonInterface {

    public ActivityAddMoneyBinding addmoneylayoutBinding;
    public CommonInterface commonInterface;
    public AddMoneyViewModel addMoneyViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addmoneylayoutBinding = ActivityAddMoneyBinding.inflate(getLayoutInflater());
        setContentView(addmoneylayoutBinding.getRoot());
        commonInterface = this;
        addMoneyViewModel = new ViewModelProvider(this).get(AddMoneyViewModel.class);
        addmoneylayoutBinding.setLifecycleOwner(this);
        addmoneylayoutBinding.setAddmoneyviewmodel(addMoneyViewModel);
        addMoneyViewModel.commonInterface = commonInterface;
        addmoneylayoutBinding.setAddmoneyviewmodel(addMoneyViewModel);
        addmoneylayoutBinding.executePendingBindings();

    }

    @Override
    public void onServiceStart() {
        addmoneylayoutBinding.pbrequestparent.setVisibility(View.VISIBLE);

        addMoneyViewModel.getAddMoneyLiveDataofParentRequest(new PrefManager(AddMoneyActivity.this).getUserID());
    }

    @Override
    public void onFailed(String msg) {
        Constant.showToast(AddMoneyActivity.this, msg);
    }

}