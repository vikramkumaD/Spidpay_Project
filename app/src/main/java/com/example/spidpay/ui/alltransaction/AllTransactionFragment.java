package com.example.spidpay.ui.alltransaction;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.constant.ItemOffsetDecoration;
import com.example.spidpay.databinding.FragmentAllTransactionBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;

import org.jetbrains.annotations.NotNull;


public class AllTransactionFragment extends Fragment {
    FragmentAllTransactionBinding fragmentAllTransactionBinding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ChangeTitlenandIconInterface changeTitlenandIconInterface;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface=(ChangeTitlenandIconInterface)context;
    }

    public AllTransactionFragment() {
        // Required empty public constructor
    }

    public static AllTransactionFragment newInstance(String param1, String param2) {
        AllTransactionFragment fragment = new AllTransactionFragment();
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
        fragmentAllTransactionBinding = FragmentAllTransactionBinding.inflate(inflater, container, false);
        View root = fragmentAllTransactionBinding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AllTransaction_Adapter allTransaction_adapter = new AllTransaction_Adapter();
        ItemOffsetDecoration itemOffsetDecoration=new ItemOffsetDecoration(getActivity(),R.dimen.marign10dp);
        fragmentAllTransactionBinding.rvAllTransaction.addItemDecoration(itemOffsetDecoration);
        fragmentAllTransactionBinding.rvAllTransaction.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentAllTransactionBinding.rvAllTransaction.setAdapter(allTransaction_adapter);
        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.alltransaction),true);
    }
}