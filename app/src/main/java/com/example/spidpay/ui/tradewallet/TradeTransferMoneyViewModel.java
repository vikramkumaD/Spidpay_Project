package com.example.spidpay.ui.tradewallet;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.request.TransferMoneyRequest;
import com.example.spidpay.data.response.TransferMoenyResponse;
import com.example.spidpay.interfaces.TradeWalletInterface;
import com.example.spidpay.util.Constant;

public class TradeTransferMoneyViewModel extends ViewModel {

    TradeWalletInterface tradeWalletInterface;
    AddMoneyRepository addMoneyRepository;
    public MutableLiveData<Boolean> total_amount = new MutableLiveData<>();
    public MutableLiveData<Boolean> other_amount = new MutableLiveData<>();
    public MutableLiveData<Boolean> spidpaywallet = new MutableLiveData<>();
    public MutableLiveData<Boolean> paytmwallet = new MutableLiveData<>();
    public MutableLiveData<Boolean> bank = new MutableLiveData<>();

    public String enteramount;
    public int totalbalance;


    public void onSpidPayWalletClick() {
        spidpaywallet.setValue(true);
        paytmwallet.setValue(false);
        bank.setValue(false);
    }


    public void onPaytmClick() {
        spidpaywallet.setValue(false);
        paytmwallet.setValue(true);
        bank.setValue(false);
    }

    public void onBankClick() {
        spidpaywallet.setValue(false);
        paytmwallet.setValue(false);
        bank.setValue(true);
    }


    public void onTotalAmountClick() {
        total_amount.setValue(true);
        other_amount.setValue(false);
    }

    public void onOtherAmountClick() {
        total_amount.setValue(false);
        other_amount.setValue(true);
    }

    public void onSubmitClick(View view) {
        if (enteramount == null && enteramount.equals("")) {
            tradeWalletInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        /*if (Integer.parseInt(enteramount) > totalbalance) {
            tradeWalletInterface.onFailed(view.getResources().getString(R.string.enteramountcannot));
            return;
        }*/

        tradeWalletInterface.onServiceStart();
    }


    public void getTransferResponse(String userId) {

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest();
        transferMoneyRequest.amount = Integer.parseInt(enteramount);
        transferMoneyRequest.transactionCategory = Constant.TRADE_TRANSFER;
        transferMoneyRequest.domain = Constant.DOMAIN_NAME;
        transferMoneyRequest.notes = "";
        transferMoneyRequest.userId = userId;


        LiveData<TransferMoenyResponse> transferMoenyResponseLiveData = addMoneyRepository.getTransferMoenyResponse(transferMoneyRequest);
        tradeWalletInterface.onTransferSuccess(transferMoenyResponseLiveData);
    }

}
