package com.example.spidpay.ui.tradewallet;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.request.BankResponse;
import com.example.spidpay.data.request.BankTransferRequest;
import com.example.spidpay.data.request.PaytmTransferRequest;
import com.example.spidpay.data.request.TransferMoneyRequest;
import com.example.spidpay.data.response.InterestedResponse;
import com.example.spidpay.data.response.TransferMoenyResponse;
import com.example.spidpay.interfaces.TradeWalletInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;

import java.util.List;
import java.util.Objects;

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


    public MutableLiveData<String> paytm_number = new MutableLiveData<>();
    public MutableLiveData<String> paytm_name = new MutableLiveData<>();
    public MutableLiveData<String> notes = new MutableLiveData<>();
    public MutableLiveData<String> enteramount = new MutableLiveData<>();
    public double totalbalance;


    public void onIMPSClick() {
        imps.setValue(true);
        //rtgs.setValue(false);
        //upi.setValue(false);
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
        //rtgs.setValue(false);
        //upi.setValue(false);
        neft.setValue(true);
    }

    public void onSpidPayWalletClick() {
        spidpaywallet.setValue(true);
        paytmwallet.setValue(false);
        bank.setValue(false);
        enteramount.setValue("");
        notes.setValue("");
    }


    public void onPaytmClick() {
        spidpaywallet.setValue(false);
        paytmwallet.setValue(true);
        bank.setValue(false);
        enteramount.setValue("");
        notes.setValue("");
    }

    public void onBankClick(String userId) {
        spidpaywallet.setValue(false);
        paytmwallet.setValue(false);
        bank.setValue(true);
        enteramount.setValue("");
        notes.setValue("");
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
        tradeWalletInterface.onServiceStart();
    }


    public void getTradeToSPWallet(Context context, String userId) {
        double amt = Double.parseDouble(Objects.requireNonNull(enteramount.getValue()));
        if (amt == 0 || amt > totalbalance) {
            tradeWalletInterface.onFailed(context.getResources().getString(R.string.enteramountcannot));
            return;
        }


        if (enteramount.getValue() == null && enteramount.getValue().equals("") && notes == null && notes.getValue().equals("")) {
            tradeWalletInterface.onFailed(context.getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest();
        transferMoneyRequest.amount = enteramount.getValue();
        transferMoneyRequest.transactionCategory = Constant.TRADE_WALLET_TRANSFER;
        transferMoneyRequest.domain = Constant.DOMAIN_NAME;
        transferMoneyRequest.notes = notes.getValue();
        transferMoneyRequest.userId = userId;

        LiveData<TransferMoenyResponse> transferMoenyResponseLiveData = addMoneyRepository.getTradeToSpidWalletTransfer(transferMoneyRequest);
        tradeWalletInterface.onTransferSuccess(transferMoenyResponseLiveData);
    }

    public void getBankTransferResponse(Context context, String userId) {
        double amt = Double.parseDouble(Objects.requireNonNull(enteramount.getValue()));
        if (amt == 0 || amt > totalbalance) {
            tradeWalletInterface.onFailed(context.getResources().getString(R.string.enteramountcannot));
            return;
        }

        if (enteramount.getValue() == null && enteramount.getValue().equals("") && notes == null && notes.getValue().equals("")) {
            tradeWalletInterface.onFailed(context.getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        TransferMoneyRequest bankTransferRequest = new TransferMoneyRequest();
        bankTransferRequest.amount = enteramount.getValue();
        bankTransferRequest.transactionCategory = Constant.TRADE_BANK_TRANSFER;
        bankTransferRequest.domain = Constant.DOMAIN_NAME;
        bankTransferRequest.notes = notes.getValue();
        bankTransferRequest.userId = userId;

        BankTransferRequest bankTransfer = new BankTransferRequest();
        bankTransfer.ifsc = "HDFC0000543";
        bankTransfer.accountNumber = "9767462028484";
        bankTransfer.beneficiaryName = "Asis Jain";
        bankTransfer.email = "Test1@gmail.com";
        bankTransfer.mobileNumber = "9663811455";
        bankTransfer.serviceCharge = "5.00";
        bankTransfer.transferMode = "IMPS";

        bankTransferRequest.bankTransfer = bankTransfer;

        LiveData<TransferMoenyResponse> transferMoenyResponseLiveData = addMoneyRepository.getTradeToSpidWalletTransfer(bankTransferRequest);
        tradeWalletInterface.onTransferSuccess(transferMoenyResponseLiveData);


    }

    public void getPaytmTransferResponse(Context context, String userId) {

        if (paytm_number.getValue() == null && paytm_number.getValue().equals("") && paytm_name.getValue() == null && paytm_name.getValue().equals("") && enteramount.getValue() == null && enteramount.getValue().equals("") && notes == null && notes.getValue().equals("")) {
            tradeWalletInterface.onFailed(context.getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        double amt = Double.parseDouble(Objects.requireNonNull(enteramount.getValue()));
        if (amt == 0 || amt > totalbalance) {
            tradeWalletInterface.onFailed(context.getResources().getString(R.string.enteramountcannot));
            return;
        }

        TransferMoneyRequest bankTransferRequest = new TransferMoneyRequest();
        bankTransferRequest.amount = enteramount.getValue();
        bankTransferRequest.transactionCategory = Constant.TRADE_PAYTM_TRANSFER;
        bankTransferRequest.domain = Constant.DOMAIN_NAME;
        bankTransferRequest.notes = notes.getValue();
        bankTransferRequest.userId = userId;

        PaytmTransferRequest paytmTransferRequest = new PaytmTransferRequest();
        paytmTransferRequest.MobileNo = paytm_number.getValue();
        paytmTransferRequest.ServiceCharge = "1";
        paytmTransferRequest.ValidateBeneficiary = true;
        paytmTransferRequest.CustomerName = paytm_name.getValue();

        bankTransferRequest.paytmTransferRequest = paytmTransferRequest;

        LiveData<TransferMoenyResponse> transferMoenyResponseLiveData = addMoneyRepository.getTradeToSpidWalletTransfer(bankTransferRequest);
        tradeWalletInterface.onTransferSuccess(transferMoenyResponseLiveData);

    }

    public void callMethodBasdedOnUserSelection(Context context, String userId) {
        if (spidpaywallet.getValue() != null && spidpaywallet.getValue()) {
            getTradeToSPWallet(context, userId);
        } else if (paytmwallet.getValue() != null && paytmwallet.getValue()) {
            getPaytmTransferResponse(context, userId);
        } else {
            getBankTransferResponse(context, userId);
        }
    }

    public LiveData<List<InterestedResponse>> getServiceCharge(String amount) {
        String txtid = "";
        if (spidpaywallet.getValue() != null && spidpaywallet.getValue()) {
            txtid = Constant.SERVICE_CHARGE_TRADE_WALLET_TRANSFER;
        } else if (paytmwallet.getValue() != null && paytmwallet.getValue()) {
            txtid = Constant.TRADE_PAYTM_TRANSFER;
        } else if (bank.getValue() != null && bank.getValue()) {
            txtid = Constant.SERVICE_CHARGE_BANK_TRANSFER;
        }
        return addMoneyRepository.getServiceCharge(amount, txtid);
    }

    public LiveData<List<BankResponse>> getUserBankList(String userid) {
        return addMoneyRepository.getUserBank(userid, Constant.BANKS);
    }


}
