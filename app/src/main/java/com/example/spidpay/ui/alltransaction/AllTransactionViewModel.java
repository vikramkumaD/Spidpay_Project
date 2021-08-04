package com.example.spidpay.ui.alltransaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.spidpay.data.repository.AllTransactionRepositroy;
import com.example.spidpay.data.response.AllTransactionResponse;
import com.example.spidpay.interfaces.AllTransactionInterface;

import java.util.List;

public class AllTransactionViewModel extends ViewModel {
    AllTransactionRepositroy allTransactionRepositroy;
    AllTransactionInterface allTransactionInterface;

    public void getAllTransactionList(String walletId, String pageno, String pageSize) {
        allTransactionInterface.onServiceStart();
        LiveData<List<AllTransactionResponse>> listLiveData = allTransactionRepositroy.getAllTransaction(walletId, pageno, pageSize);
        allTransactionInterface.onSuccess(listLiveData);
    }

   /* @BindingAdapter("app:change")
    public static void changeImage(ImageView imageView, String transactionType) {
        if (transactionType.equals(Constant.CREDIT)) {
            imageView.setImageResource(R.drawable.mynetwork);
        } else {
            //imageView.setImageResource(R.drawable.ic_baseline_horizontal_rule_24);
        }
    }*/

}
