package com.example.spidpay.ui.profile.kyc;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.MyProfileRepository;
import com.example.spidpay.data.response.CompanyReponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.databinding.FragmentKYCBinding;
import com.example.spidpay.databinding.UpdateBankDetailBinding;
import com.example.spidpay.databinding.UpdateKycLayoutBinding;
import com.example.spidpay.interfaces.KYCInterface;
import com.example.spidpay.ui.profile.MyProfileViewModel;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;


public class KYCFragment extends Fragment implements KYCInterface {
    FragmentKYCBinding fragmentKYCBinding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    KYCInterface kycInterface;
    MyProfileViewModel myProfileViewModel;
    MyProfileRepository myProfileRepository;
    BottomSheetDialog bottomSheetDialog_kyc, bottomSheetDialog_company;
    UpdateKycLayoutBinding updateKycLayoutBinding;
    UpdateBankDetailBinding updateBankDetailBinding;
    KYCResponse kycResponse;
    CompanyReponse companyReponse;


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
        myProfileRepository = new MyProfileRepository(requireActivity(), kycInterface);
        myProfileViewModel.myProfileRepository = myProfileRepository;
        myProfileViewModel.kycInterface = kycInterface;
        myProfileViewModel.getKYCInfo(new PrefManager(requireContext()).getUserID());
        getViewLifecycleOwner();

    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentKYCBinding.imgEditKyc.setOnClickListener(v -> update_KYC());
        fragmentKYCBinding.imgEditCompanyInfo.setOnClickListener(v -> updateCompanyDetail());
    }

    @Override
    public void onSuccess(LiveData<KYCResponse> kycResponseLiveData) {
        fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
        kycResponseLiveData.observe(this, kycResponse -> {
            if (kycResponse.panNo != null && !kycResponse.panNo.equals("")) {
                fragmentKYCBinding.setKycinfo(kycResponse);
                this.kycResponse = kycResponse;
                myProfileViewModel.getCompanyInfo(new PrefManager(getContext()).getUserID());
            }
        });
    }

    @Override
    public void onUpdateSucess(LiveData<UpdateResponse> updateResponseLiveData) {
        updateResponseLiveData.observe(this, updateResponse -> {
            if (updateResponse.userid.equals(new PrefManager(getContext()).getUserID())) ;
            bottomSheetDialog_kyc.dismiss();
            myProfileViewModel.getKYCInfo(updateResponse.userid);
        });
    }


    @Override
    public void onCompanySuccess(LiveData<CompanyReponse> companyReponseLiveData) {
        fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
        companyReponseLiveData.observe(this, companyReponse -> {
            if (companyReponse.companyName != null && !companyReponse.companyName.equals("")) {
                fragmentKYCBinding.setCompanyinfo(companyReponse);
                this.companyReponse = companyReponse;
            }
        });
    }

    @Override
    public void onCompanyUpdateSucess(LiveData<UpdateResponse> updateResponseLiveData) {
        updateResponseLiveData.observe(this, updateResponse -> {
            if (updateResponse.userid.equals(new PrefManager(getContext()).getUserID())) {
                bottomSheetDialog_company.dismiss();
                myProfileViewModel.getCompanyInfo(updateResponse.userid);
            }
        });
    }

    @Override
    public void onServiceStart() {
        fragmentKYCBinding.pbKyc.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        Constant.showToast(getContext(), msg);
        fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
    }

    public void update_KYC() {
        bottomSheetDialog_kyc = new BottomSheetDialog(requireContext());
        updateKycLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.update_kyc_layout, null, false);
        bottomSheetDialog_kyc.setContentView(updateKycLayoutBinding.getRoot());
        updateKycLayoutBinding.setMyprofileviewmodel(myProfileViewModel);
        updateKycLayoutBinding.setKyc(kycResponse);
        myProfileViewModel.userid = new PrefManager(getContext()).getUserID();
        updateKycLayoutBinding.setLifecycleOwner(this);
        bottomSheetDialog_kyc.show();
        updateKycLayoutBinding.imgDismissDialog.setOnClickListener(v -> bottomSheetDialog_kyc.dismiss());

    }

    public void updateCompanyDetail() {
        bottomSheetDialog_company = new BottomSheetDialog(requireContext());
        updateBankDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.update_bank_detail, null, false);
        bottomSheetDialog_company.setContentView(updateBankDetailBinding.getRoot());
        updateBankDetailBinding.setMyprofileviewmodel(myProfileViewModel);
        updateBankDetailBinding.setCompany(companyReponse);
        myProfileViewModel.userid = new PrefManager(getContext()).getUserID();
        updateBankDetailBinding.setLifecycleOwner(this);
        bottomSheetDialog_company.show();
        updateBankDetailBinding.imgDismissDialog.setOnClickListener(v -> bottomSheetDialog_company.dismiss());

    }
}