package com.example.spidpay.ui.spwallet.cashdeposite;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.data.response.AddMoneyResponse;
import com.example.spidpay.databinding.CashDepositeActivityBinding;
import com.example.spidpay.db.AppDatabase;
import com.example.spidpay.db.UserDao;
import com.example.spidpay.interfaces.AddMoneyInterface;
import com.example.spidpay.interfaces.OnStaticClickIterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.ui.spwallet.AddMoneyViewModel;
import com.example.spidpay.ui.signup.InterrestedforAdapter;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class CashDespositActivity extends AppCompatActivity implements OnStaticClickIterface, StaticInterface, AddMoneyInterface {
    CashDepositeActivityBinding cashDepositeActivityBinding;
    String balance;
    BottomSheetDialog interrestedfor_bottomsheet;
    AddMoneyViewModel addMoneyViewModel;
    StaticInterface staticInterface;
    OnStaticClickIterface onStaticClickIterface;
    StaticRepository staticRepository;
    AddMoneyInterface addMoneyInterface;
    AddMoneyRepository addMoneyRepository;
    private boolean isbanklistcalled = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onStaticClickIterface = this;
        addMoneyInterface = this;
        staticInterface = CashDespositActivity.this;
        cashDepositeActivityBinding = CashDepositeActivityBinding.inflate(getLayoutInflater());
        setContentView(cashDepositeActivityBinding.getRoot());
        cashDepositeActivityBinding.imgBackpress.setOnClickListener(v -> finish());
        balance = getIntent().getStringExtra("balance");

        AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "spidpay_db").build();
        UserDao userDao = appDatabase.getUserDao();
        addMoneyRepository = new AddMoneyRepository(CashDespositActivity.this, addMoneyInterface);
        staticRepository = new StaticRepository(CashDespositActivity.this, staticInterface);
        addMoneyViewModel = new ViewModelProvider(this).get(AddMoneyViewModel.class);
        addMoneyViewModel.addMoneyRepository = addMoneyRepository;
        addMoneyViewModel.staticInterface = staticInterface;
        addMoneyViewModel.staticRepository = staticRepository;
        addMoneyViewModel.addMoneyInterface = addMoneyInterface;
        cashDepositeActivityBinding.setAddmoneyviewmodel(addMoneyViewModel);
        cashDepositeActivityBinding.tvCashdepositeBalance.setText(balance);

        new Thread(() -> cashDepositeActivityBinding.setUser(userDao.getUser())).start();


        cashDepositeActivityBinding.executePendingBindings();
        cashDepositeActivityBinding.setLifecycleOwner(this);
        cashDepositeActivityBinding.edtBankList.setOnClickListener(v -> {
            isbanklistcalled = false;
            getBanKList();
        });
        cashDepositeActivityBinding.edtCashdepositTransctiontype.setOnClickListener(v -> {
            isbanklistcalled = true;
            getTransactionType();
        });
    }

    public void getBanKList() {
        addMoneyViewModel.static_value = Constant.BANK;
        View view = LayoutInflater.from(CashDespositActivity.this).inflate(R.layout.interrestedfor_bottomsheet, null);
        interrestedfor_bottomsheet = new BottomSheetDialog(CashDespositActivity.this);
        interrestedfor_bottomsheet.setContentView(view);
        interrestedfor_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView rv_interreseted_for = view.findViewById(R.id.rv_interreseted_for);
        rv_interreseted_for.setLayoutManager(new LinearLayoutManager(CashDespositActivity.this));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(CashDespositActivity.this, R.dimen.marign10dp);
        rv_interreseted_for.addItemDecoration(itemOffsetDecoration);


        addMoneyViewModel.getStaticData().observe(this, interrestedforResponses -> {
            cashDepositeActivityBinding.pbCashdeposit.setVisibility(View.GONE);
            Constant.START_TOUCH(CashDespositActivity.this);
            InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onStaticClickIterface);
            rv_interreseted_for.setAdapter(interrestedforAdapter);
            interrestedfor_bottomsheet.show();
        });

    }

    public void getTransactionType() {
        addMoneyViewModel.static_value = Constant.BANK;
        View view = LayoutInflater.from(CashDespositActivity.this).inflate(R.layout.interrestedfor_bottomsheet, null);
        interrestedfor_bottomsheet = new BottomSheetDialog(CashDespositActivity.this);
        interrestedfor_bottomsheet.setContentView(view);
        interrestedfor_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView rv_interreseted_for = view.findViewById(R.id.rv_interreseted_for);
        rv_interreseted_for.setLayoutManager(new LinearLayoutManager(CashDespositActivity.this));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(CashDespositActivity.this, R.dimen.marign10dp);
        rv_interreseted_for.addItemDecoration(itemOffsetDecoration);


        addMoneyViewModel.getTransactionType().observe(this, interrestedforResponses -> {
            cashDepositeActivityBinding.pbCashdeposit.setVisibility(View.GONE);
            Constant.START_TOUCH(CashDespositActivity.this);
            InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onStaticClickIterface);
            rv_interreseted_for.setAdapter(interrestedforAdapter);
            interrestedfor_bottomsheet.show();
        });

    }

    @Override
    public void onItemClick(String code, String description) {
        if (isbanklistcalled) {
            addMoneyViewModel.transactiontype = code;
            addMoneyViewModel.transactiontype = description;
            cashDepositeActivityBinding.edtCashdepositTransctiontype.setText(description);
        } else {
            addMoneyViewModel.bankcode = code;
            addMoneyViewModel.bankname = description;
            cashDepositeActivityBinding.edtBankList.setText(description);
        }
        interrestedfor_bottomsheet.dismiss();
    }

    @Override
    public void onStaticStart() {
        Constant.STOP_TOUCH(CashDespositActivity.this);
        cashDepositeActivityBinding.pbCashdeposit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStaticFailed(String msg) {
        Constant.START_TOUCH(CashDespositActivity.this);
        cashDepositeActivityBinding.pbCashdeposit.setVisibility(View.GONE);
    }

    @Override
    public void onOnlineSuccess(LiveData<AddMoneyResponse> addMoneyResponseLiveData) {
        addMoneyResponseLiveData.observe(this, addMoneyResponse -> {
            Constant.START_TOUCH(CashDespositActivity.this);
            cashDepositeActivityBinding.pbCashdeposit.setVisibility(View.GONE);
        });
    }

    @Override
    public void onServiceStart() {
        Constant.STOP_TOUCH(CashDespositActivity.this);
        cashDepositeActivityBinding.pbCashdeposit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        Constant.START_TOUCH(CashDespositActivity.this);
        cashDepositeActivityBinding.pbCashdeposit.setVisibility(View.GONE);
        Constant.showToast(CashDespositActivity.this, msg);
    }

}