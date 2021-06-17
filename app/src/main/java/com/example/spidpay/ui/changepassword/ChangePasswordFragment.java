package com.example.spidpay.ui.changepassword;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.data.response.CommonResponse;
import com.example.spidpay.databinding.FragmentChangePasswordBinding;
import com.example.spidpay.databinding.FragmentTransferMoneyBinding;
import com.example.spidpay.interfaces.ChangePasswordInterface;
import com.example.spidpay.util.Constant;

import org.jetbrains.annotations.NotNull;


public class ChangePasswordFragment extends Fragment implements ChangePasswordInterface {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    FragmentChangePasswordBinding fragmentChangePasswordBinding;
    ChangePasswordViewModel changePasswordViewModel;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentChangePasswordBinding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        return fragmentChangePasswordBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onSuccess(LiveData<CommonResponse> commonResponseLiveData) {
        commonResponseLiveData.observe(this, commonResponse -> {

        });
    }

    @Override
    public void onServiceStart() {

    }

    @Override
    public void onFailed(String msg) {
        Constant.showToast(requireContext(), msg);
    }
}