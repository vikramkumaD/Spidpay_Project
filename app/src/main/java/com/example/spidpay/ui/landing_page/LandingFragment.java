package com.example.spidpay.ui.landing_page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spidpay.R;
import com.example.spidpay.databinding.LandingfragmentBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;


public class LandingFragment extends Fragment {
    BottomSheetDialog main_wallet_popup, trade_wallet_popup;
    private LandingfragmentBinding landingfragmentBinding;
    ChangeTitlenandIconInterface changeTitlenandIconInterface;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface = (ChangeTitlenandIconInterface) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        landingfragmentBinding = LandingfragmentBinding.inflate(inflater, container, false);
        return landingfragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        landingfragmentBinding.imgMainwalletPopoup.setOnClickListener(v -> showMainWalletPopup());
        landingfragmentBinding.imgTradewalletPopoup.setOnClickListener(v -> showTradeWalletPopup());
        changeTitlenandIconInterface.changeTitlenadIcon("", false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        landingfragmentBinding = null;
    }

    public void showMainWalletPopup() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.wallet_bottomsheet, null);
        main_wallet_popup = new BottomSheetDialog(requireActivity());
        main_wallet_popup.setContentView(view);
        main_wallet_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        main_wallet_popup.show();
        TextView tv_wallet_addmoney = view.findViewById(R.id.tv_wallet_addmoney);
        tv_wallet_addmoney.setOnClickListener(v -> {
            main_wallet_popup.dismiss();
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.addMoneyFragment);
        });
        TextView tv_wallet_viewalltrasaction = view.findViewById(R.id.tv_wallet_viewalltrasaction);
        tv_wallet_viewalltrasaction.setOnClickListener(v -> {
            main_wallet_popup.dismiss();
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.allTransactionFragment);
        });
        TextView tv_wallet_requestwallet_transfer = view.findViewById(R.id.tv_wallet_requestwallet_transfer);
        tv_wallet_requestwallet_transfer.setOnClickListener(v -> {
            main_wallet_popup.dismiss();
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.request_Wallet_Transfer_Fragment);
        });

        TextView tv_wallet_setlowbalancealert = view.findViewById(R.id.tv_wallet_setlowbalancealert);
        tv_wallet_setlowbalancealert.setOnClickListener(v -> {
            main_wallet_popup.dismiss();
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.low_Balance_Fragment);
        });

    }

    public void showTradeWalletPopup() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.trade_wallet_bottomsheet, null);
        trade_wallet_popup = new BottomSheetDialog(requireActivity());
        trade_wallet_popup.setContentView(view);
        trade_wallet_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        trade_wallet_popup.show();

        TextView tv_trade_trasnfertobank=view.findViewById(R.id.tv_trade_trasnfertobank);
        tv_trade_trasnfertobank.setOnClickListener(v -> {
            trade_wallet_popup.dismiss();
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.transferToBanKFragment);
        });

        TextView tv_wallet_viewalltrasaction = view.findViewById(R.id.tv_trade_viewalltransaction);
        tv_wallet_viewalltrasaction.setOnClickListener(v -> {
            trade_wallet_popup.dismiss();
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.allTransactionFragment);
        });
    }

}