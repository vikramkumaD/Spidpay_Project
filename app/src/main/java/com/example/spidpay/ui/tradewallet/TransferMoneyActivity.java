package com.example.spidpay.ui.tradewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.AddMoneyRepository;
import com.example.spidpay.data.request.BankResponse;
import com.example.spidpay.data.response.InterestedResponse;
import com.example.spidpay.data.response.TransferMoenyResponse;
import com.example.spidpay.databinding.ActivityTranferMoneyBinding;
import com.example.spidpay.interfaces.OnStaticClickIterface;
import com.example.spidpay.interfaces.OnUserBankClick;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.interfaces.TradeWalletInterface;
import com.example.spidpay.ui.signup.InterrestedforAdapter;
import com.example.spidpay.ui.spwallet.PaymentSuccessfulActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class TransferMoneyActivity extends AppCompatActivity implements TradeWalletInterface, OnUserBankClick,StaticInterface, OnStaticClickIterface {
    ActivityTranferMoneyBinding activityTranferMoneyBinding;
    TradeTransferMoneyViewModel tradeTransferMoneyViewModel;
    TradeWalletInterface tradeWalletInterface;
    AddMoneyRepository addMoneyRepository;
    BottomSheetDialog interrestedfor_bottomsheet;
    StaticInterface staticInterface;
    OnStaticClickIterface onStaticClickIterface;
    OnUserBankClick onUserBankClick;
    List<BankResponse> bankResponseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTranferMoneyBinding = ActivityTranferMoneyBinding.inflate(getLayoutInflater());
        setContentView(activityTranferMoneyBinding.getRoot());
        bankResponseList = new ArrayList<>();
        tradeTransferMoneyViewModel = new ViewModelProvider(this).get(TradeTransferMoneyViewModel.class);
        tradeWalletInterface = this;
        onUserBankClick= this;
        staticInterface = this;
        onStaticClickIterface = this;
        addMoneyRepository = new AddMoneyRepository(TransferMoneyActivity.this, tradeWalletInterface);
        activityTranferMoneyBinding.setTransfermoney(tradeTransferMoneyViewModel);
        tradeTransferMoneyViewModel.tradeWalletInterface = tradeWalletInterface;
        tradeTransferMoneyViewModel.addMoneyRepository = addMoneyRepository;
        activityTranferMoneyBinding.setLifecycleOwner(this);
        activityTranferMoneyBinding.setTotalamount(getIntent().getStringExtra("balance"));
        activityTranferMoneyBinding.imgBackpress.setOnClickListener(v -> finish());
        tradeTransferMoneyViewModel.onSpidPayWalletClick();
        tradeTransferMoneyViewModel.onTotalAmountClick();
        tradeTransferMoneyViewModel.enteramount.setValue(getIntent().getStringExtra("balance"));
        tradeTransferMoneyViewModel.totalbalance = Double.parseDouble(getIntent().getStringExtra("balance"));
        activityTranferMoneyBinding.setUserId(new PrefManager(this).getUserID());

        tradeTransferMoneyViewModel.paytmwallet.observe(this, aBoolean -> {
            if (aBoolean) {
                tradeTransferMoneyViewModel.getServiceCharge(activityTranferMoneyBinding.edtTranferAmount.getText().toString()).observe(TransferMoneyActivity.this, new Observer<List<InterestedResponse>>() {
                    @Override
                    public void onChanged(List<InterestedResponse> interestedResponses) {
                        if (interestedResponses.size() > 0) {
                            Log.e("ok", "ok");
                        }
                    }
                });

            }
        });


        tradeTransferMoneyViewModel.bank.observe(this, aBoolean -> {
            if (aBoolean) {

                BankResponse bankResponse=new BankResponse();
                bankResponse.accountNo="123456";
                bankResponse.accountHolderName="Vikram";
                bankResponse.branchName="Kandiwali";
                bankResponse.ifscCode="MAHB001";

                InterestedResponse interestedResponse=new InterestedResponse();
                interestedResponse.code="1234";
                interestedResponse.description="PNB Bnak";

                bankResponse.bankName=interestedResponse;
                bankResponseList.add(bankResponse);

                BankResponse bankResponse1=new BankResponse();
                bankResponse1.accountNo="7890123";
                bankResponse1.accountHolderName="Shivam";
                bankResponse1.branchName="Malad";
                bankResponse1.ifscCode="MAHB003";

                InterestedResponse interestedResponse1=new InterestedResponse();
                interestedResponse1.code="1235";
                interestedResponse1.description="SBI Bank";

                bankResponse1.bankName=interestedResponse1;
                bankResponseList.add(bankResponse1);


                for (BankResponse bank : bankResponseList) {
                    activityTranferMoneyBinding.tvUserBankName.setText(bank.bankName.description);
                    activityTranferMoneyBinding.tvUserAccNumber.setText(String.valueOf(bank.accountNo));
                    activityTranferMoneyBinding.tvUserBankIfsccode.setText(bank.ifscCode);
                    tradeTransferMoneyViewModel.ifsc=bank.ifscCode;
                    tradeTransferMoneyViewModel.accountNumber=String.valueOf(bank.accountNo);
                    tradeTransferMoneyViewModel.beneficiaryName=bank.accountHolderName;
                    break;
                }


              /*
                tradeTransferMoneyViewModel.getUserBankList(new PrefManager(this).getUserID()).observe(this, bankResponses -> {
                    for (BankResponse bank : bankResponses) {
                        bankResponseList.addAll(bankResponses);
                        activityTranferMoneyBinding.tvUserBankName.setText(bank.bankName.description);
                        activityTranferMoneyBinding.tvUserAccNumber.setText(String.valueOf(bank.accountNo));
                        activityTranferMoneyBinding.tvUserBankIfsccode.setText(bank.ifscCode);
                        tradeTransferMoneyViewModel.ifsc=bank.ifscCode;
                        tradeTransferMoneyViewModel.accountNumber=String.valueOf(bank.accountNo);
                        tradeTransferMoneyViewModel.beneficiaryName=bank.accountHolderName;

                        break;
                    }
                });
              */


            }
        });

        activityTranferMoneyBinding.ivExpandMore.setOnClickListener(v -> {
            if (bankResponseList.size() > 0) {
                getUserBankList(bankResponseList);
            } else {
                tradeTransferMoneyViewModel.getUserBankList(new PrefManager(TransferMoneyActivity.this).getUserID()).observe(TransferMoneyActivity.this, new Observer<List<BankResponse>>() {
                    @Override
                    public void onChanged(List<BankResponse> bankResponses) {
                        for (BankResponse bank : bankResponses) {
                            bankResponseList.addAll(bankResponses);
                            activityTranferMoneyBinding.tvUserBankName.setText(bank.bankName.description);
                            activityTranferMoneyBinding.tvUserAccNumber.setText(String.valueOf(bank.accountNo));
                            activityTranferMoneyBinding.tvUserBankIfsccode.setText(bank.ifscCode);
                            break;
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onServiceStart() {
        activityTranferMoneyBinding.pbTransferMoney.setVisibility(View.VISIBLE);
        Constant.STOP_TOUCH(TransferMoneyActivity.this);
        tradeTransferMoneyViewModel.callMethodBasdedOnUserSelection(this, new PrefManager(TransferMoneyActivity.this).getUserID());
    }

    @Override
    public void onFailed(String msg) {
        activityTranferMoneyBinding.pbTransferMoney.setVisibility(View.GONE);
        Constant.showToast(TransferMoneyActivity.this, msg);
        Constant.START_TOUCH(TransferMoneyActivity.this);
    }

    @Override
    public void onTransferSuccess(LiveData<TransferMoenyResponse> liveData) {
        liveData.observe(this, transferMoenyResponse -> {
            if (transferMoenyResponse.transactionId != null && !transferMoenyResponse.transactionId.equals("")) {
                activityTranferMoneyBinding.pbTransferMoney.setVisibility(View.GONE);
                Constant.START_TOUCH(TransferMoneyActivity.this);
                Intent intent = new Intent(TransferMoneyActivity.this, PaymentSuccessfulActivity.class);
                intent.putExtra("amount", transferMoenyResponse.amount);
                intent.putExtra("wallet_flag", false);
                intent.putExtra("datetime", Constant.getCurrentDateTime());
                intent.putExtra("title", true);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onStaticStart() {

    }

    @Override
    public void onStaticFailed(String msg) {

    }

    @Override
    public void onItemClick(String code, String description) {
        interrestedfor_bottomsheet.dismiss();
    }


    private void getUserBankList(List<BankResponse> list) {
        View view = LayoutInflater.from(TransferMoneyActivity.this).inflate(R.layout.interrestedfor_bottomsheet, null);
        interrestedfor_bottomsheet = new BottomSheetDialog(TransferMoneyActivity.this);
        interrestedfor_bottomsheet.setContentView(view);
        interrestedfor_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView rv_interested_for = view.findViewById(R.id.rv_interreseted_for);
        rv_interested_for.setLayoutManager(new LinearLayoutManager(TransferMoneyActivity.this));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(TransferMoneyActivity.this, R.dimen.margin10dp);
        rv_interested_for.addItemDecoration(itemOffsetDecoration);
        BankListAdapter bankListAdapter = new BankListAdapter(list,onUserBankClick);
        rv_interested_for.setAdapter(bankListAdapter);
        interrestedfor_bottomsheet.show();
    }

    @Override
    public void onBankClick(String bankname, String accountnumber, String username,String ifsccode) {
        tradeTransferMoneyViewModel.ifsc=ifsccode;
        tradeTransferMoneyViewModel.accountNumber=String.valueOf(accountnumber);
        tradeTransferMoneyViewModel.beneficiaryName=username;

        activityTranferMoneyBinding.tvUserBankName.setText(bankname);
        activityTranferMoneyBinding.tvUserAccNumber.setText(String.valueOf(accountnumber));
        activityTranferMoneyBinding.tvUserBankIfsccode.setText(ifsccode);
        interrestedfor_bottomsheet.dismiss();
    }
}