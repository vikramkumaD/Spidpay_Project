package com.example.spidpay.ui.alltransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.AllTransactionRepositroy;
import com.example.spidpay.data.response.AllTransactionResponse;
import com.example.spidpay.databinding.ActivityGetAllTransactionByWalletIdBinding;
import com.example.spidpay.interfaces.AllTransactionInterface;
import com.example.spidpay.ui.spwallet.AddMoneyActivity;
import com.example.spidpay.ui.tradewallet.TransferMoneyActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.ItemOffsetDecoration;

import java.util.List;

public class AllTransactionByWalletIdAcivity extends AppCompatActivity implements AllTransactionInterface {
    AllTransactionInterface allTransactionInterface;
    AllTransactionRepositroy allTransactionRepositroy;
    ActivityGetAllTransactionByWalletIdBinding activityGetAllTransactionByWalletIdBinding;
    AllTransactionViewModel allTransactionViewModel;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGetAllTransactionByWalletIdBinding = ActivityGetAllTransactionByWalletIdBinding.inflate(getLayoutInflater());
        setContentView(activityGetAllTransactionByWalletIdBinding.getRoot());
        allTransactionInterface = this;
        allTransactionRepositroy = new AllTransactionRepositroy(AllTransactionByWalletIdAcivity.this, allTransactionInterface);
        allTransactionViewModel = new ViewModelProvider(this).get(AllTransactionViewModel.class);
        allTransactionViewModel.allTransactionInterface = allTransactionInterface;
        allTransactionViewModel.allTransactionRepositroy = allTransactionRepositroy;
        activityGetAllTransactionByWalletIdBinding.setLifecycleOwner(this);
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(AllTransactionByWalletIdAcivity.this, R.dimen.marign10dp);
        activityGetAllTransactionByWalletIdBinding.rvAllTransaction.addItemDecoration(itemOffsetDecoration);
        flag=getIntent().getBooleanExtra("flag", false);
        if(flag)
        {
            activityGetAllTransactionByWalletIdBinding.tvWalletAddmoney.setText("Transfer Money");
        }
        allTransactionViewModel.getAllTransactionList(getIntent().getStringExtra("walletId"), "1", "10");
        activityGetAllTransactionByWalletIdBinding.tvWalletBalanceAmount.setText(getResources().getString(R.string.rupess) + getIntent().getStringExtra("balance"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        activityGetAllTransactionByWalletIdBinding.imgBackpress.setOnClickListener(v -> finish());
        activityGetAllTransactionByWalletIdBinding.linearAddmoney.setOnClickListener(v -> {
            if (flag)
                startActivity(new Intent(AllTransactionByWalletIdAcivity.this, TransferMoneyActivity.class));
            else
                startActivity(new Intent(AllTransactionByWalletIdAcivity.this, AddMoneyActivity.class));
        });


    }

    @Override
    public void onSuccess(LiveData<List<AllTransactionResponse>> allTransactionResponses) {
        allTransactionResponses.observe(this, allTransactionResponses1 -> {
            Constant.START_TOUCH(AllTransactionByWalletIdAcivity.this);
            activityGetAllTransactionByWalletIdBinding.pbAllTransaction.setVisibility(View.GONE);
            activityGetAllTransactionByWalletIdBinding.rvAllTransaction.setLayoutManager(new LinearLayoutManager(AllTransactionByWalletIdAcivity.this));
            AllTransactionAdapter allTransactionAdapter = new AllTransactionAdapter(allTransactionResponses1);
            activityGetAllTransactionByWalletIdBinding.rvAllTransaction.setAdapter(allTransactionAdapter);
        });
    }

    @Override
    public void onServiceStart() {
        Constant.START_TOUCH(AllTransactionByWalletIdAcivity.this);
        activityGetAllTransactionByWalletIdBinding.pbAllTransaction.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        Constant.START_TOUCH(AllTransactionByWalletIdAcivity.this);
        activityGetAllTransactionByWalletIdBinding.pbAllTransaction.setVisibility(View.GONE);
        Constant.showToast(AllTransactionByWalletIdAcivity.this, msg);
    }
}