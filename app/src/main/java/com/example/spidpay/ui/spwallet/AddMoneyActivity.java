package com.example.spidpay.ui.spwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.data.response.AddMoneyResponse;
import com.example.spidpay.data.response.ParentUser;
import com.example.spidpay.data.response.UserInfo;
import com.example.spidpay.databinding.ActivityAddMoneyBinding;
import com.example.spidpay.db.AppDatabase;
import com.example.spidpay.db.UserDao;
import com.example.spidpay.interfaces.AddMoneyInterface;
import com.example.spidpay.interfaces.CommonInterface;
import com.example.spidpay.ui.spwallet.cashdeposite.CashDespositActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;

public class AddMoneyActivity extends AppCompatActivity implements CommonInterface, AddMoneyInterface {

    public ActivityAddMoneyBinding addmoneylayoutBinding;
    public CommonInterface commonInterface;
    public AddMoneyViewModel addMoneyViewModel;
    UserDao userDao;
    UserInfo userInfo;
    ParentUser parentUser;
    AddMoneyRepository addMoneyRepository;
    AddMoneyInterface addMoneyInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addmoneylayoutBinding = ActivityAddMoneyBinding.inflate(getLayoutInflater());
        setContentView(addmoneylayoutBinding.getRoot());
        commonInterface = this;
        addMoneyInterface=this;
        addMoneyRepository = new AddMoneyRepository(AddMoneyActivity.this, addMoneyInterface);
        addMoneyViewModel = new ViewModelProvider(this).get(AddMoneyViewModel.class);
        addmoneylayoutBinding.setLifecycleOwner(this);
        addmoneylayoutBinding.setAddmoneyviewmodel(addMoneyViewModel);
        addMoneyViewModel.commonInterface = commonInterface;
        addMoneyViewModel.addMoneyRepository=addMoneyRepository;
        addMoneyViewModel.addMoneyInterface=addMoneyInterface;
        addmoneylayoutBinding.setAddmoneyviewmodel(addMoneyViewModel);
        addmoneylayoutBinding.executePendingBindings();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
        userDao = db.getUserDao();
        new Thread(() -> {
            userInfo = userDao.getUser();
            parentUser = userDao.getParent();
        }).start();

    }

    @Override
    public void onServiceStart() {
        addmoneylayoutBinding.pbrequestparent.setVisibility(View.VISIBLE);
        addMoneyViewModel.getAddMoneyLiveDataofParentRequest(new PrefManager(AddMoneyActivity.this).getUserID(), userInfo, parentUser);
    }

    @Override
    public void onFailed(String msg) {
        Constant.showToast(AddMoneyActivity.this, msg);
        Constant.START_TOUCH(AddMoneyActivity.this);
        addmoneylayoutBinding.pbrequestparent.setVisibility(View.GONE);
    }

    @Override
    public void onOnlineSuccess(LiveData<AddMoneyResponse> addMoneyResponseLiveData) {
        addMoneyResponseLiveData.observe(this, addMoneyResponse -> {
            if(addMoneyResponse!=null && !addMoneyResponse.txnId.equals(""))
            {
                Constant.START_TOUCH(AddMoneyActivity.this);
                addmoneylayoutBinding.pbrequestparent.setVisibility(View.GONE);
                Intent intent = new Intent(AddMoneyActivity.this, PaymentSuccessfulActivity.class);
                intent.putExtra("amount", addMoneyResponse.amount);
                intent.putExtra("datetime", addMoneyResponse.creationTime);
                intent.putExtra("title", true);
                startActivity(intent);
                finish();
            }
        });
    }
}