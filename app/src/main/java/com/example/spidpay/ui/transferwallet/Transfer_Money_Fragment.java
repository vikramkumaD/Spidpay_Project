package com.example.spidpay.ui.transferwallet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.databinding.FragmentTransferMoneyBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Transfer_Money_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Transfer_Money_Fragment extends Fragment {

    FragmentTransferMoneyBinding fragmentTransferMoneyBinding;
    ChangeTitlenandIconInterface changeTitlenandIconInterface;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Transfer_Money_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface = (ChangeTitlenandIconInterface) context;
    }

    public static Transfer_Money_Fragment newInstance(String param1, String param2) {
        Transfer_Money_Fragment fragment = new Transfer_Money_Fragment();
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
        fragmentTransferMoneyBinding = FragmentTransferMoneyBinding.inflate(inflater, container, false);
        return fragmentTransferMoneyBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentTransferMoneyBinding.tvAlertMsg.setText(getResources().getString(R.string.transfermsg));
        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.transfertowallet), true);
    }
}