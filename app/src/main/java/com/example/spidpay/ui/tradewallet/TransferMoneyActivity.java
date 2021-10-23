package com.example.spidpay.ui.tradewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.response.InterrestedforResponse;
import com.example.spidpay.data.response.TransferMoenyResponse;
import com.example.spidpay.databinding.ActivityTranferMoneyBinding;
import com.example.spidpay.interfaces.OnStaticClickIterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.interfaces.TradeWalletInterface;
import com.example.spidpay.ui.signup.InterrestedforAdapter;
import com.example.spidpay.ui.spwallet.PaymentSuccessfulActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
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

        activityTranferMoneyBinding.btnSelectbank.setOnClickListener(v -> getBank());

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

    public void getBank() {

        View view = LayoutInflater.from(TransferMoneyActivity.this).inflate(R.layout.interrestedfor_bottomsheet, null);
        interrestedfor_bottomsheet = new BottomSheetDialog(TransferMoneyActivity.this);
        interrestedfor_bottomsheet.setContentView(view);
        interrestedfor_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView rv_interreseted_for = view.findViewById(R.id.rv_interreseted_for);
        rv_interreseted_for.setLayoutManager(new LinearLayoutManager(TransferMoneyActivity.this));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(TransferMoneyActivity.this, R.dimen.margin10dp);
        rv_interreseted_for.addItemDecoration(itemOffsetDecoration);

        List<InterrestedforResponse> list = new ArrayList<>();
        list.add(new InterrestedforResponse("ICICI BANK", "User 1"));
        list.add(new InterrestedforResponse("SBI BANK", "User 2"));
        InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(list, onStaticClickIterface);
        rv_interreseted_for.setAdapter(interrestedforAdapter);
        interrestedfor_bottomsheet.show();
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