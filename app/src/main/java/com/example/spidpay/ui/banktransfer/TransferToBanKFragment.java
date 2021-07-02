package com.example.spidpay.ui.banktransfer;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spidpay.R;
import com.example.spidpay.databinding.FragmentTransferMoneyBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;

import org.jetbrains.annotations.NotNull;

public class TransferToBanKFragment extends Fragment {
    FragmentTransferMoneyBinding fragmentTransferMoneyBinding;
    ChangeTitlenandIconInterface changeTitlenandIconInterface;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface=(ChangeTitlenandIconInterface)context;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        fragmentTransferMoneyBinding=FragmentTransferMoneyBinding.inflate(inflater,container,false);
        return fragmentTransferMoneyBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.bankalertmsg));
        ForegroundColorSpan foregroundColorSpanCyan = new ForegroundColorSpan(getResources().getColor(R.color.black));
        ForegroundColorSpan foregroundColorSpanBlue = new ForegroundColorSpan(getResources().getColor(R.color.light_green));
        spannableString.setSpan(foregroundColorSpanCyan, 0, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColorSpanBlue, 43, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        fragmentTransferMoneyBinding.tvAlertMsg.setText(spannableString);
        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.transfertobank),true);


    }
}
