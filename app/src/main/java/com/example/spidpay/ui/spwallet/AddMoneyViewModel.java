package com.example.spidpay.ui.addmoney;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.data.response.InterrestedforResponse;
import com.example.spidpay.data.response.UserData;
import com.example.spidpay.interfaces.AddMoneyInterface;
import com.example.spidpay.interfaces.CommonInterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.ui.addmoney.online.OnlineActivity;
import com.example.spidpay.util.Constant;

import java.util.List;

public class AddMoneyViewModel extends ViewModel {
    public MutableLiveData<Boolean> online = new MutableLiveData<>();
    public MutableLiveData<Boolean> cashdeposite = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestparent = new MutableLiveData<>();
    public MutableLiveData<String> online_money = new MutableLiveData<>();
    public MutableLiveData<String> online_notes = new MutableLiveData<>();

    CommonInterface commonInterface;
    public AddMoneyRepository addMoneyRepository;
    public StaticInterface staticInterface;
    public StaticRepository staticRepository;
    public AddMoneyInterface addMoneyInterface;

    public String static_value, bankrefid;


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

    public void onButtonContinueClick(View view,String balance,String walletid) {
        if (online.getValue() == null || cashdeposite.getValue() == null || requestparent.getValue() == null) {
            commonInterface.onFailed(view.getContext().getResources().getString(R.string.selectoneoptin));
            return;
        }

        if (online.getValue()) {
            view.getContext().startActivity(new Intent(view.getContext(), OnlineActivity.class));
        }


        commonInterface.onServiceStart();
    }


    public void onTenThousandClick(View view) {
        online_money.setValue("10000");
    }

    public void onTwentyThousandClick(View view) {
        online_money.setValue("20000");
    }

    public void onThirtyThousandClick(View view) {
        online_money.setValue("30000");
    }

    public void onTransferToSPClick() {

    }

    public void onTransferToBank() {
    }

    public void validate_CashDeposite_Filed(View view, UserData userData) {

    }

    public LiveData<List<InterrestedforResponse>> getStaticData() {
        staticInterface.onStaticStart();
        LiveData<List<InterrestedforResponse>> interrestedforliveData = staticRepository.getStaticData(Constant.USER, Constant.ROLE_INTERRESTEDFOR);
        return interrestedforliveData;
    }

}

