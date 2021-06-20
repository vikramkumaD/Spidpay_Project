package com.example.spidpay.ui.profile.kyc;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.MyProfileRepository;
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.data.response.CompanyReponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.databinding.FragmentKYCBinding;
import com.example.spidpay.databinding.UpdateCompanyDetailBinding;
import com.example.spidpay.databinding.UpdateKycLayoutBinding;
import com.example.spidpay.interfaces.KYCInterface;
import com.example.spidpay.interfaces.OnStaticClickIterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.ui.profile.MyProfileViewModel;
import com.example.spidpay.ui.signup.InterrestedforAdapter;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;


public class KYCFragment extends Fragment implements KYCInterface, OnStaticClickIterface, StaticInterface {
    FragmentKYCBinding fragmentKYCBinding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    KYCInterface kycInterface;
    MyProfileViewModel myProfileViewModel;
    MyProfileRepository myProfileRepository;
    BottomSheetDialog bottomSheetDialog_kyc, bottomSheetDialog_company;
    UpdateKycLayoutBinding updateKycLayoutBinding;
    UpdateCompanyDetailBinding updateBankDetailBinding;
    KYCResponse kycResponse;
    CompanyReponse companyReponse;
    BottomSheetDialog interrestedfor_bottomsheet;
    OnStaticClickIterface onStaticClickIterface;
    StaticInterface staticInterface;
    StaticRepository staticRepository;

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
        onStaticClickIterface = KYCFragment.this;
        staticInterface = (StaticInterface) KYCFragment.this;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentKYCBinding = FragmentKYCBinding.inflate(inflater, container, false);
        return fragmentKYCBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myProfileViewModel = new ViewModelProvider(requireActivity()).get(MyProfileViewModel.class);
        staticRepository = new StaticRepository(requireActivity(), staticInterface);
        myProfileRepository = new MyProfileRepository(requireActivity(), kycInterface);
        myProfileViewModel.myProfileRepository = myProfileRepository;
        myProfileViewModel.kycInterface = kycInterface;
        myProfileViewModel.staticInterface = staticInterface;

        myProfileViewModel.staticRepository = staticRepository;
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
    public void onKYCSuccess(LiveData<KYCResponse> kycResponseLiveData) {
        kycResponseLiveData.observe(this, kycResponse -> {
            if (kycResponse.panNo != null && !kycResponse.panNo.equals("")) {
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.setKycinfo(kycResponse);
                this.kycResponse = kycResponse;
                myProfileViewModel.getCompanyInfo(new PrefManager(getContext()).getUserID());
            } else {
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                this.kycResponse = new KYCResponse();
            }
        });
    }

    @Override
    public void onUpdateSucess(LiveData<UpdateResponse> updateResponseLiveData) {
        updateResponseLiveData.observe(this, updateResponse -> {
            if (updateResponse.userid.equals(new PrefManager(getContext()).getUserID())) {
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                bottomSheetDialog_kyc.dismiss();
                myProfileViewModel.getKYCInfo(updateResponse.userid);
            }
        });
    }


    @Override
    public void onCompanySuccess(LiveData<CompanyReponse> companyReponseLiveData) {

        companyReponseLiveData.observe(this, companyReponse -> {
            if (companyReponse.companyName != null && !companyReponse.companyName.equals("")) {
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.setCompanyinfo(companyReponse);
                this.companyReponse = companyReponse;
                myProfileViewModel.code = companyReponse.companyType.code;
            } else {
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                Constant.START_TOUCH(requireActivity());
                this.companyReponse = new CompanyReponse();
            }
        });
    }

    @Override
    public void onCompanyUpdateSucess(LiveData<UpdateResponse> updateResponseLiveData) {
        updateResponseLiveData.observe(this, updateResponse -> {
            if (updateResponse.userid.equals(new PrefManager(getContext()).getUserID())) {
                bottomSheetDialog_company.dismiss();
                myProfileViewModel.getCompanyInfo(updateResponse.userid);
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onServiceStart() {
        Constant.STOP_TOUCH(requireActivity());
        fragmentKYCBinding.pbKyc.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        Constant.START_TOUCH(requireActivity());
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
        updateBankDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.update_company_detail, null, false);
        bottomSheetDialog_company.setContentView(updateBankDetailBinding.getRoot());
        updateBankDetailBinding.setMyprofileviewmodel(myProfileViewModel);
        updateBankDetailBinding.setCompany(companyReponse);
        myProfileViewModel.userid = new PrefManager(getContext()).getUserID();
        updateBankDetailBinding.setLifecycleOwner(this);
        bottomSheetDialog_company.show();
        updateBankDetailBinding.imgDismissDialog.setOnClickListener(v -> bottomSheetDialog_company.dismiss());
        updateBankDetailBinding.edtEditComType.setOnClickListener(v -> {
            myProfileViewModel.static_value = Constant.COMPANY;
            getCompayType();
        });
    }

    public void getCompayType() {
        View view = LayoutInflater.from(requireActivity()).inflate(R.layout.interrestedfor_bottomsheet, null);
        interrestedfor_bottomsheet = new BottomSheetDialog(requireActivity());
        interrestedfor_bottomsheet.setContentView(view);
        interrestedfor_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView rv_interreseted_for = view.findViewById(R.id.rv_interreseted_for);
        rv_interreseted_for.setLayoutManager(new LinearLayoutManager(requireActivity()));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.marign10dp);
        rv_interreseted_for.addItemDecoration(itemOffsetDecoration);

        myProfileViewModel.getStaticData().observe(this, interrestedforResponses -> {
            Constant.START_TOUCH(requireActivity());
            fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
            InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onStaticClickIterface);
            rv_interreseted_for.setAdapter(interrestedforAdapter);
            interrestedfor_bottomsheet.show();
        });

    }

    @Override
    public void onItemClick(String code, String description) {
        myProfileViewModel.code = code;
        updateBankDetailBinding.edtEditComType.setText(description);
        interrestedfor_bottomsheet.dismiss();
    }


    @Override
    public void onStaticStart() {
        Constant.STOP_TOUCH(requireActivity());
        fragmentKYCBinding.pbKyc.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStaticFailed(String msg) {
        Constant.START_TOUCH(requireActivity());
        Constant.showToast(requireActivity(), msg);
        fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
    }
}