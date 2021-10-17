package com.example.spidpay.ui.landing_page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.LandingRepository;
import com.example.spidpay.data.response.WalletResponse;
import com.example.spidpay.interfaces.LandingInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.databinding.LandingfragmentBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;
import com.example.spidpay.interfaces.UpdateBottomView;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class LandingFragment extends Fragment implements LandingInterface {
    BottomSheetDialog main_wallet_popup, trade_wallet_popup;
    public LandingfragmentBinding landingfragmentBinding;
    ChangeTitlenandIconInterface changeTitlenandIconInterface;
    UpdateBottomView updateBottomView;
    LandingViewModel landingViewModel;
    LandingRepository landingRepository;
    LandingInterface landingInterface;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface = (ChangeTitlenandIconInterface) context;
        updateBottomView = (UpdateBottomView) context;
        landingInterface = LandingFragment.this;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        landingfragmentBinding = LandingfragmentBinding.inflate(inflater, container, false);
        return landingfragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        landingfragmentBinding.relativeAeps.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.AEPS_Dashboard_Fragment);
        });

        landingRepository = new LandingRepository(requireActivity(), landingInterface);
        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);
        landingViewModel.landingInterface = landingInterface;
        landingViewModel.landingRepository = landingRepository;
        landingfragmentBinding.setLandingviewmodel(landingViewModel);
        landingViewModel.getWalletResponse(new PrefManager(requireActivity()).getUserID());

    }

    @Override
    public void onResume() {
        super.onResume();
        new PrefManager(requireActivity()).setIsLandingPageOpen(true);
        changeTitlenandIconInterface.changeTitlenadIcon("", false);
        updateBottomView.bottomViewId(Constant.BOTTOM_HOME);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        landingfragmentBinding = null;
    }



    @Override
    public void onSuccess(LiveData<List<WalletResponse>> listLiveData) {
        listLiveData.observe(this, walletResponses -> {
            Constant.START_TOUCH(requireActivity());
            landingfragmentBinding.pbLandingpage.setVisibility(View.GONE);
            landingfragmentBinding.setWallet(walletResponses);
        });
    }

    @Override
    public void onServiceStart() {
        Constant.START_TOUCH(requireActivity());
        landingfragmentBinding.pbLandingpage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        Constant.START_TOUCH(requireActivity());
        landingfragmentBinding.pbLandingpage.setVisibility(View.GONE);
    }
}