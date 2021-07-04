package com.example.spidpay.ui.addmoney.cashdeposite;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.databinding.CashDepositeActivityBinding;
import com.example.spidpay.interfaces.OnStaticClickIterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.ui.addmoney.AddMoneyViewModel;
import com.example.spidpay.ui.signup.InterrestedforAdapter;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class CashDespositActivity extends AppCompatActivity implements OnStaticClickIterface, StaticInterface {
    CashDepositeActivityBinding cashDepositeActivityBinding;
    String balance;
    BottomSheetDialog interrestedfor_bottomsheet;
    AddMoneyViewModel addMoneyViewModel;
    StaticInterface staticInterface;
    OnStaticClickIterface onStaticClickIterface;
    StaticRepository staticRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onStaticClickIterface = this;
        staticInterface = CashDespositActivity.this;
        cashDepositeActivityBinding = CashDepositeActivityBinding.inflate(getLayoutInflater());
        setContentView(cashDepositeActivityBinding.getRoot());
        cashDepositeActivityBinding.imgBackpress.setOnClickListener(v -> finish());
        balance = getIntent().getStringExtra("balance");

        staticRepository = new StaticRepository(CashDespositActivity.this, staticInterface);
        addMoneyViewModel = new ViewModelProvider(this).get(AddMoneyViewModel.class);
        addMoneyViewModel.staticInterface = staticInterface;
        addMoneyViewModel.staticRepository = staticRepository;
        cashDepositeActivityBinding.setAddmoneyviewmodel(addMoneyViewModel);
        cashDepositeActivityBinding.tvCashdepositeBalance.setText(balance);
        cashDepositeActivityBinding.executePendingBindings();
        cashDepositeActivityBinding.setLifecycleOwner(this);
        cashDepositeActivityBinding.edtBankList.setOnClickListener(v -> getBanKList());
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

    @Override
    public void onItemClick(String code, String description) {
        addMoneyViewModel.bankcode = code;
        cashDepositeActivityBinding.edtBankList.setText(description);
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
}