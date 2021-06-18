package com.example.spidpay.ui.profile.kyc;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.MyProfileRepository;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.databinding.FragmentKYCBinding;
import com.example.spidpay.interfaces.KYCInterface;
import com.example.spidpay.ui.profile.MyProfileViewModel;
import com.example.spidpay.util.PrefManager;

import org.jetbrains.annotations.NotNull;


public class KYCFragment extends Fragment implements KYCInterface {
    FragmentKYCBinding fragmentKYCBinding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    KYCInterface kycInterface;
    MyProfileViewModel myProfileViewModel;
    MyProfileRepository myProfileRepository;

    public KYCFragment() {

    }

    public static KYCFragment newInstance(String param1, String param2) {
        KYCFragment fragment = new KYCFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        kycInterface = KYCFragment.this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentKYCBinding = FragmentKYCBinding.inflate(inflater, container, false);
        return fragmentKYCBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myProfileViewModel = new ViewModelProvider(requireActivity()).get(MyProfileViewModel.class);
        myProfileRepository=new MyProfileRepository(requireActivity(),kycInterface);
        myProfileViewModel.myProfileRepository = myProfileRepository;
        myProfileViewModel.kycInterface = kycInterface;
        myProfileViewModel.getKYCInfo(new PrefManager(requireContext()).getUserID());
        getViewLifecycleOwner();

    }

    @Override
    public void onSuccess(LiveData<KYCResponse> kycResponseLiveData) {
        fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
        kycResponseLiveData.observe(this, kycResponse -> {
            if (kycResponse.panNo != null && !kycResponse.panNo.equals("")) {
                fragmentKYCBinding.setKycinfo(kycResponse);
            }
        });
    }

    @Override
    public void onServiceStart() {
        fragmentKYCBinding.pbKyc.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
    }
}