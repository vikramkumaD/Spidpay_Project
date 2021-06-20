package com.example.spidpay.ui.profile.bankgst;

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
import com.example.spidpay.data.response.BankDetailsResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.databinding.FragmentBankGSTBinding;
import com.example.spidpay.databinding.UpdateBankDetailBinding;
import com.example.spidpay.interfaces.BankInteface;
import com.example.spidpay.interfaces.OnStaticClickIterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.ui.profile.MyProfileViewModel;
import com.example.spidpay.ui.signup.InterrestedforAdapter;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

public class BankGSTFragment extends Fragment implements BankInteface, StaticInterface, OnStaticClickIterface {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentBankGSTBinding fragmentBankGSTBinding;
    MyProfileViewModel myProfileViewModel;
    BankInteface bankInteface;
    UpdateBankDetailBinding updateBankDetailBinding;
    BottomSheetDialog bottomSheetDialog_bankinfo;
    BankDetailsResponse bankDetailsResponse;
    BottomSheetDialog interrestedfor_bottomsheet;
    OnStaticClickIterface onStaticClickIterface;
    StaticInterface staticInterface;
    StaticRepository staticRepository;


    public BankGSTFragment() {
    }

    public static BankGSTFragment newInstance(String param1, String param2) {
        BankGSTFragment fragment = new BankGSTFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        bankInteface = BankGSTFragment.this;
        onStaticClickIterface = (OnStaticClickIterface) BankGSTFragment.this;
        staticInterface = (StaticInterface) BankGSTFragment.this;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBankGSTBinding = FragmentBankGSTBinding.inflate(inflater, container, false);
        return fragmentBankGSTBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        staticRepository = new StaticRepository(requireActivity(), staticInterface);
        MyProfileRepository myProfileRepository = new MyProfileRepository(getContext(), bankInteface);
        myProfileViewModel = new ViewModelProvider(requireActivity()).get(MyProfileViewModel.class);
        myProfileViewModel.bankInteface = bankInteface;
        myProfileViewModel.myProfileRepository = myProfileRepository;
        myProfileViewModel.staticRepository = staticRepository;
        myProfileViewModel.staticInterface=staticInterface;
        myProfileViewModel.getBankInfo(new PrefManager(getContext()).getUserID());
        getViewLifecycleOwner();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentBankGSTBinding.imgEditCompany.setOnClickListener(v -> update_Bank_Deatil());
    }

    @Override
    public void onSuccess(LiveData<BankDetailsResponse> bankDetailsResponseLiveData) {
        bankDetailsResponseLiveData.observe(this, bankDetailsResponse -> {
            if (bankDetailsResponse.accountHolderName != null && !bankDetailsResponse.accountHolderName.equals("")) {
                Constant.START_TOUCH(requireActivity());
                fragmentBankGSTBinding.pbBankinfo.setVisibility(View.GONE);
                fragmentBankGSTBinding.setBankinfo(bankDetailsResponse);
                this.bankDetailsResponse = bankDetailsResponse;
                myProfileViewModel.code=bankDetailsResponse.companyType.code;
            } else {
                Constant.START_TOUCH(requireActivity());
                fragmentBankGSTBinding.pbBankinfo.setVisibility(View.GONE);
                this.bankDetailsResponse = new BankDetailsResponse();
            }
        });

    }

    @Override
    public void onUpdateSuccess(LiveData<UpdateResponse> updateResponseLiveData) {
        updateResponseLiveData.observe(this, updateResponse -> {
            if (updateResponse.userid.equals(new PrefManager(requireActivity()).getUserID())) {
                bottomSheetDialog_bankinfo.dismiss();
                Constant.START_TOUCH(requireActivity());
                myProfileViewModel.getBankInfo(updateResponse.userid);
            }
        });
    }

    @Override
    public void onServiceStart() {
        Constant.STOP_TOUCH(requireActivity());
        fragmentBankGSTBinding.pbBankinfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        Constant.START_TOUCH(requireActivity());
        Constant.showToast(getContext(), msg);
        fragmentBankGSTBinding.pbBankinfo.setVisibility(View.GONE);
    }

    public void update_Bank_Deatil() {
        bottomSheetDialog_bankinfo = new BottomSheetDialog(requireContext());
        updateBankDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.update_bank_detail, null, false);
        bottomSheetDialog_bankinfo.setContentView(updateBankDetailBinding.getRoot());
        updateBankDetailBinding.setMyprofileviewmodel(myProfileViewModel);
        updateBankDetailBinding.setBank(bankDetailsResponse);
        myProfileViewModel.userid = new PrefManager(getContext()).getUserID();
        updateBankDetailBinding.setLifecycleOwner(this);
        bottomSheetDialog_bankinfo.show();
        updateBankDetailBinding.imgDismissDialog.setOnClickListener(v -> bottomSheetDialog_bankinfo.dismiss());
        updateBankDetailBinding.edtUserBankName.setOnClickListener(v -> getCompayType());

    }

    public void getCompayType() {
        myProfileViewModel.static_value = Constant.BANK;
        View view = LayoutInflater.from(requireActivity()).inflate(R.layout.interrestedfor_bottomsheet, null);
        interrestedfor_bottomsheet = new BottomSheetDialog(requireActivity());
        interrestedfor_bottomsheet.setContentView(view);
        interrestedfor_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView rv_interreseted_for = view.findViewById(R.id.rv_interreseted_for);
        rv_interreseted_for.setLayoutManager(new LinearLayoutManager(requireActivity()));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.marign10dp);
        rv_interreseted_for.addItemDecoration(itemOffsetDecoration);

        myProfileViewModel.getStaticData().observe(this, interrestedforResponses -> {
            fragmentBankGSTBinding.pbBankinfo.setVisibility(View.GONE);
            Constant.START_TOUCH(requireActivity());
            InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onStaticClickIterface);
            rv_interreseted_for.setAdapter(interrestedforAdapter);
            interrestedfor_bottomsheet.show();
        });

    }

    @Override
    public void onItemClick(String code, String description) {
        myProfileViewModel.code = code;
        updateBankDetailBinding.edtUserBankName.setText(description);
        interrestedfor_bottomsheet.dismiss();
    }

    @Override
    public void onStaticStart() {
        Constant.STOP_TOUCH(requireActivity());
        fragmentBankGSTBinding.pbBankinfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStaticFailed(String msg) {
        Constant.START_TOUCH(requireActivity());
        Constant.showToast(requireActivity(), msg);
        fragmentBankGSTBinding.pbBankinfo.setVisibility(View.GONE);
    }
}