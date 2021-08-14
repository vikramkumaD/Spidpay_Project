package com.example.spidpay.ui.spwallet;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.data.request.AddMoneyRequest;
import com.example.spidpay.data.request.CashTransactionRequest;
import com.example.spidpay.data.request.WalletTransferRequest;
import com.example.spidpay.data.response.AddMoneyResponse;
import com.example.spidpay.data.response.InterrestedforResponse;
import com.example.spidpay.data.response.ParentUser;
import com.example.spidpay.data.response.UserInfo;
import com.example.spidpay.interfaces.AddMoneyInterface;
import com.example.spidpay.interfaces.CommonInterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.ui.spwallet.cashdeposite.CashDespositActivity;
import com.example.spidpay.util.Constant;

import java.util.List;

public class AddMoneyViewModel extends ViewModel {
    public MutableLiveData<Boolean> online = new MutableLiveData<>();
    public MutableLiveData<Boolean> cashdeposite = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestparent = new MutableLiveData<>();
    public MutableLiveData<String> online_money = new MutableLiveData<>();
    public MutableLiveData<String> online_notes = new MutableLiveData<>();
    public MutableLiveData<String> bankrefid = new MutableLiveData<>();
    CommonInterface commonInterface;
    public AddMoneyRepository addMoneyRepository;
    public StaticInterface staticInterface;
    public StaticRepository staticRepository;
    public AddMoneyInterface addMoneyInterface;
    public String static_value, transactiontype, bankcode, bankname;

    public void onDepositeCashClick() {
        online.setValue(false);
        cashdeposite.setValue(true);
        requestparent.setValue(false);
    }

    public void onOnlineClick() {
        online.setValue(true);
        cashdeposite.setValue(false);
        requestparent.setValue(false);
    }

    public void onRequestParentClick() {
        online.setValue(false);
        cashdeposite.setValue(false);
        requestparent.setValue(true);
    }

    public void onButtonContinueClick(View view, String balance, String walletid) {
        if (online.getValue() == null || cashdeposite.getValue() == null || requestparent.getValue() == null) {
            commonInterface.onFailed(view.getContext().getResources().getString(R.string.selectoneoptin));
            return;
        }

        if (online.getValue()) {
            Intent intent = new Intent(view.getContext(), WebviewActivity.class);
            intent.putExtra("walletid", walletid);
            view.getContext().startActivity(intent);

            return;
        }
        if (cashdeposite.getValue()) {
            Intent intent = new Intent(view.getContext(), CashDespositActivity.class);
            intent.putExtra("walletid", walletid);
            intent.putExtra("balance", balance);
            view.getContext().startActivity(intent);

            return;
        }

        commonInterface.onServiceStart();
    }


    public void validate_CashDeposite_Filed(View view) {
        if (online_notes.getValue() == null || online_notes.getValue().equals("") ||
                online_money.getValue() == null || online_money.getValue().equals("") ||
                bankcode == null || bankcode.equals("") ||
                transactiontype == null || transactiontype.equals("") ||
                bankrefid.getValue() == null || bankrefid.getValue().equals("")) {
            addMoneyInterface.onFailed(view.getResources().getString(R.string.filedcannotbeblank));
            return;
        }

        addMoneyInterface.onServiceStart();
    }

    public void getAddMoneyLiveDataofCashDeposite(String userid) {
        AddMoneyRequest addMoneyRequest = new AddMoneyRequest();
        addMoneyRequest.amount = online_money.getValue();
        addMoneyRequest.domain = Constant.DOMAIN_NAME;
        addMoneyRequest.notes = online_notes.getValue();
        addMoneyRequest.userId = userid;
        addMoneyRequest.walletType = "SP";
        addMoneyRequest.transactionCategory = Constant.TRANSACTION_CATEGORY_CASHDEPOSITE;
        CashTransactionRequest cashTransactionRequest = new CashTransactionRequest();
        cashTransactionRequest.bankName = bankname;
        cashTransactionRequest.bankReferenceId = bankrefid.getValue();
        cashTransactionRequest.dateTime = Constant.getCurrentDateTime();
        cashTransactionRequest.imageRef = "/abc";
        cashTransactionRequest.transferMode = transactiontype;
        addMoneyRequest.cashTransactionRequest = cashTransactionRequest;
        LiveData<AddMoneyResponse> addMoneyResponseLiveData = addMoneyRepository.getAddModenyResponse(addMoneyRequest);
        addMoneyInterface.onOnlineSuccess(addMoneyResponseLiveData);
    }

    public void getAddMoneyLiveDataofParentRequest(String userid, UserInfo userInfo, ParentUser parentUser) {
        AddMoneyRequest addMoneyRequest = new AddMoneyRequest();
        addMoneyRequest.amount = online_money.getValue();
        addMoneyRequest.domain = Constant.DOMAIN_NAME;
        addMoneyRequest.notes = online_notes.getValue();
        addMoneyRequest.userId = userid;
        addMoneyRequest.walletType = "SP";
        addMoneyRequest.transactionCategory = Constant.TRANSACTION_CATEGORY_WALLETTRANSFER;

        WalletTransferRequest walletTransferRequest = new WalletTransferRequest();
        walletTransferRequest.targetWalletId = "";
        walletTransferRequest.firstName = userInfo.firstName;
        walletTransferRequest.lastName = userInfo.lastName;
        walletTransferRequest.userId = userid;
        walletTransferRequest.otherUserId = parentUser.userId;
        walletTransferRequest.otherUserFirstName = parentUser.firstName;
        walletTransferRequest.otherUserLastName = parentUser.lastName;
        walletTransferRequest.partyType = "REQUESTER";
        addMoneyRequest.walletTransferRequest = walletTransferRequest;


        LiveData<AddMoneyResponse> addMoneyResponseLiveData = addMoneyRepository.getAddModenyResponse(addMoneyRequest);
        addMoneyInterface.onOnlineSuccess(addMoneyResponseLiveData);
    }

    public LiveData<List<InterrestedforResponse>> getStaticData() {
        staticInterface.onStaticStart();
        return staticRepository.getStaticData(Constant.BANK, Constant.ROLE_INTERRESTEDFOR);
    }

}

