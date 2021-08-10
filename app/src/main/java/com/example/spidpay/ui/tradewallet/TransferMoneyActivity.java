package com.example.spidpay.ui.tradewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.response.TransferMoenyResponse;
import com.example.spidpay.databinding.ActivityTranferMoneyBinding;
import com.example.spidpay.interfaces.TradeWalletInterface;
import com.example.spidpay.ui.spwallet.AddMoneyViewModel;
import com.example.spidpay.ui.spwallet.PaymentSuccessfulActivity;
import com.example.spidpay.ui.spwallet.cashdeposite.CashDespositActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;


public class TransferMoneyActivity extends AppCompatActivity implements TradeWalletInterface {
    ActivityTranferMoneyBinding activityTranferMoneyBinding;
    TradeTransferMoneyViewModel tradeTransferMoneyViewModel;
    TradeWalletInterface tradeWalletInterface;
    AddMoneyRepository addMoneyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTranferMoneyBinding = ActivityTranferMoneyBinding.inflate(getLayoutInflater());
        setContentView(activityTranferMoneyBinding.getRoot());
        tradeTransferMoneyViewModel = new ViewModelProvider(this).get(TradeTransferMoneyViewModel.class);
        tradeWalletInterface = this;
        addMoneyRepository = new AddMoneyRepository(TransferMoneyActivity.this, tradeWalletInterface);
        activityTranferMoneyBinding.setTransfermoney(tradeTransferMoneyViewModel);
        tradeTransferMoneyViewModel.tradeWalletInterface = tradeWalletInterface;
        tradeTransferMoneyViewModel.addMoneyRepository = addMoneyRepository;
        activityTranferMoneyBinding.setLifecycleOwner(this);
        activityTranferMoneyBinding.setTotalamount(getIntent().getStringExtra("balance"));
        activityTranferMoneyBinding.imgBackpress.setOnClickListener(v -> finish());
        tradeTransferMoneyViewModel.onSpidPayWalletClick();
        tradeTransferMoneyViewModel.onTotalAmountClick();
        tradeTransferMoneyViewModel.enteramount.setValue(getIntent().getStringExtra("balance"));
        tradeTransferMoneyViewModel.totalbalance = Double.parseDouble(getIntent().getStringExtra("balance"));
    }

    @Override
    public void onServiceStart() {
        activityTranferMoneyBinding.pbTransferMoney.setVisibility(View.VISIBLE);
        Constant.STOP_TOUCH(TransferMoneyActivity.this);
        tradeTransferMoneyViewModel.getTransferResponse(new PrefManager(TransferMoneyActivity.this).getUserID());
    }

    @Override
    public void onFailed(String msg) {
        activityTranferMoneyBinding.pbTransferMoney.setVisibility(View.GONE);
        Constant.showToast(TransferMoneyActivity.this, msg);
        Constant.START_TOUCH(TransferMoneyActivity.this);
    }

    @Override
    public void onTransferSuccess(LiveData<TransferMoenyResponse> liveData) {
        liveData.observe(this, transferMoenyResponse -> {
            if (transferMoenyResponse.transactionId != null && !transferMoenyResponse.transactionId.equals("")) {
                activityTranferMoneyBinding.pbTransferMoney.setVisibility(View.GONE);
                Constant.START_TOUCH(TransferMoneyActivity.this);
                Intent intent = new Intent(TransferMoneyActivity.this, PaymentSuccessfulActivity.class);
                intent.putExtra("amount", transferMoenyResponse.amount);
                intent.putExtra("wallet_flag", false);
                intent.putExtra("datetime", "2021-08-10 10:20:12");
                intent.putExtra("title", true);
                startActivity(intent);
                finish();
            }
        });
    }
}