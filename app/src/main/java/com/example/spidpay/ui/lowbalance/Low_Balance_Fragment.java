package com.example.spidpay.ui.lowbalance;

import android.content.Context;
import android.os.Bundle;
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

public class Low_Balance_Fragment extends Fragment {
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
        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.lowbalancemsg2),true);
        fragmentTransferMoneyBinding.btnProceed.setText(getResources().getString(R.string.setalert));
    }
}
