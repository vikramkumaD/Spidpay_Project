package com.example.spidpay.ui.tradewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.response.InterestedResponse;
import com.example.spidpay.data.response.TransferMoenyResponse;
import com.example.spidpay.databinding.ActivityTranferMoneyBinding;
import com.example.spidpay.interfaces.OnStaticClickIterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.interfaces.TradeWalletInterface;
import com.example.spidpay.ui.spwallet.PaymentSuccessfulActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;


public class TransferMoneyActivity extends AppCompatActivity implements TradeWalletInterface, StaticInterface, OnStaticClickIterface {
    ActivityTranferMoneyBinding activityTranferMoneyBinding;
    TradeTransferMoneyViewModel tradeTransferMoneyViewModel;
    TradeWalletInterface tradeWalletInterface;
    AddMoneyRepository addMoneyRepository;
    BottomSheetDialog interrestedfor_bottomsheet;
    StaticInterface staticInterface;
    OnStaticClickIterface onStaticClickIterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTranferMoneyBinding = ActivityTranferMoneyBinding.inflate(getLayoutInflater());
        setContentView(activityTranferMoneyBinding.getRoot());
        tradeTransferMoneyViewModel = new ViewModelProvider(this).get(TradeTransferMoneyViewModel.class);
        tradeWalletInterface = this;
        staticInterface = this;
        onStaticClickIterface = this;
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

        activityTranferMoneyBinding.edtTranferAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tradeTransferMoneyViewModel.getServiceCharge(s.toString()).observe(TransferMoneyActivity.this, new Observer<List<InterestedResponse>>() {
                        @Override
                        public void onChanged(List<InterestedResponse> interestedResponses) {
                            if (interestedResponses.size() > 0) {

                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onServiceStart() {
        activityTranferMoneyBinding.pbTransferMoney.setVisibility(View.VISIBLE);
        Constant.STOP_TOUCH(TransferMoneyActivity.this);
        tradeTransferMoneyViewModel.callMethodBasdedOnUserSelection(new PrefManager(TransferMoneyActivity.this).getUserID());
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
                intent.putExtra("datetime", Constant.getCurrentDateTime());
                intent.putExtra("title", true);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onStaticStart() {

    }

    @Override
    public void onStaticFailed(String msg) {

    }

    @Override
    public void onItemClick(String code, String description) {
        interrestedfor_bottomsheet.dismiss();
    }
}