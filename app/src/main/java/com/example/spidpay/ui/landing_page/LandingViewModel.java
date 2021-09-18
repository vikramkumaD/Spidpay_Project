package com.example.spidpay.ui.landing_page;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spidpay.data.repository.LandingRepository;
import com.example.spidpay.data.response.WalletResponse;
import com.example.spidpay.interfaces.LandingInterface;
import com.example.spidpay.ui.alltransaction.AllTransactionByWalletIdAcivity;

import java.util.List;

public class LandingViewModel extends ViewModel {

    LandingInterface landingInterface;
    LandingRepository landingRepository;

    public void getWalletResponse(String userid) {
        landingInterface.onServiceStart();
        LiveData<List<WalletResponse>> liveData = landingRepository.getWalletResponse(userid);
        landingInterface.onSuccess(liveData);
    }

    public void openAllTransactionAcivity(View view, String walletid, String balance, String type, boolean flag) {
        Intent intent = new Intent(view.getContext(), AllTransactionByWalletIdAcivity.class);
        intent.putExtra("walletId", walletid);
        intent.putExtra("balance", balance);
        intent.putExtra("flag", flag);
        view.getContext().startActivity(intent);
    }


}
