package com.example.spidpay.ui.tradewallet;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.request.BankTransferRequest;
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

    public MutableLiveData<Boolean> imps = new MutableLiveData<>();
    public MutableLiveData<Boolean> upi = new MutableLiveData<>();
    public MutableLiveData<Boolean> neft = new MutableLiveData<>();
    public MutableLiveData<Boolean> rtgs = new MutableLiveData<>();


    public MutableLiveData<String> notes = new MutableLiveData<>();
    public MutableLiveData<String> enteramount = new MutableLiveData<>();
    public double totalbalance;


    public void onIMPSClick() {
        imps.setValue(true);
        rtgs.setValue(false);
        upi.setValue(false);
        neft.setValue(false);
    }

    public void onUPIClick() {
        imps.setValue(false);
        rtgs.setValue(false);
        upi.setValue(true);
        neft.setValue(false);
    }

    public void onRTGSClick() {
        imps.setValue(false);
        rtgs.setValue(true);
        upi.setValue(false);
        neft.setValue(false);
    }

    public void onNEFTClick() {
        imps.setValue(false);
        rtgs.setValue(false);
        upi.setValue(false);
        neft.setValue(true);
    }

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
        enteramount.setValue(String.valueOf(totalbalance));
    }

    public void onOtherAmountClick() {
        total_amount.setValue(false);
        other_amount.setValue(true);
        enteramount.setValue("");

    }

    public void onSubmitClick(View view) {
        if (enteramount == null && enteramount.equals("")) {
            tradeWalletInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        if (Integer.parseInt(enteramount.getValue()) == 0 || Integer.parseInt(enteramount.getValue()) > totalbalance) {
            tradeWalletInterface.onFailed(view.getResources().getString(R.string.enteramountcannot));
            return;
        }


        tradeWalletInterface.onServiceStart();
    }


    public void getTransferResponse(String userId) {

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest();
        transferMoneyRequest.amount = Integer.parseInt(enteramount.getValue());
        transferMoneyRequest.transactionCategory = Constant.TRADE_TRANSFER;
        transferMoneyRequest.domain = Constant.DOMAIN_NAME;
        transferMoneyRequest.notes = notes.getValue();
        transferMoneyRequest.userId = userId;

        LiveData<TransferMoenyResponse> transferMoenyResponseLiveData = addMoneyRepository.getTradeToSpidWalletTransfer(transferMoneyRequest);
        tradeWalletInterface.onTransferSuccess(transferMoenyResponseLiveData);
    }

    public void getBankTransferResponse(String userId) {
        TransferMoneyRequest bankTransferRequest = new TransferMoneyRequest();
        bankTransferRequest.amount = Integer.parseInt(enteramount.getValue());
        bankTransferRequest.transactionCategory = Constant.BANK_TRANSFER;
        bankTransferRequest.domain = Constant.DOMAIN_NAME;
        bankTransferRequest.notes = notes.getValue();
        bankTransferRequest.userId = userId;

        BankTransferRequest bankTransfer = new BankTransferRequest();
        bankTransfer.ifsc = "SBIN000067";
        bankTransfer.accountNumber = "6736437647636";
        bankTransfer.beneficiaryName = "Sanjeev Kumar";
        bankTransfer.email = "info@spidpay.in";
        bankTransfer.mobileNumber = "3333333333";
        bankTransfer.serviceCharge = "10.00";
        bankTransfer.transferMode = "IMPS";

        bankTransferRequest.bankTransfer = bankTransfer;

        LiveData<TransferMoenyResponse> transferMoenyResponseLiveData = addMoneyRepository.getTradeToSpidWalletTransfer(bankTransferRequest);
        tradeWalletInterface.onTransferSuccess(transferMoenyResponseLiveData);


    }

    public void callMethodBasdedOnUserSelection(String userId)
    {
        if(spidpaywallet.getValue())
        {
            getTransferResponse(userId);
        }
        else {
            getBankTransferResponse(userId);
        }

    }
}
