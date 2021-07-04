package com.example.spidpay.ui.landing_page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.spidpay.R;
import com.example.spidpay.data.repository.LandingRepository;
import com.example.spidpay.data.response.UserData;
import com.example.spidpay.data.response.WalletResponse;
import com.example.spidpay.db.AppDatabase;
import com.example.spidpay.db.UserDao;
import com.example.spidpay.interfaces.LandingInterface;
import com.example.spidpay.util.Constant;
import com.example.spidpay.databinding.LandingfragmentBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;
import com.example.spidpay.interfaces.UpdateBottomView;
import com.example.spidpay.util.PrefManager;
import org.jetbrains.annotations.NotNull;
import java.util.List;


public class LandingFragment extends Fragment implements LandingInterface {
    private LandingfragmentBinding landingfragmentBinding;
    ChangeTitlenandIconInterface changeTitlenandIconInterface;
    UpdateBottomView updateBottomView;
    LandingViewModel landingViewModel;
    LandingRepository landinRepository;
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

        landinRepository = new LandingRepository(requireActivity(), landingInterface);
        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);
        landingViewModel.landingInterface = landingInterface;
        landingViewModel.landinRepository = landinRepository;
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

  /*  public void showMainWalletPopup() {
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

        TextView tv_trade_trasnfertobank = view.findViewById(R.id.tv_trade_trasnfertobank);
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
    }*/

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