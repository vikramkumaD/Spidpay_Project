package com.example.spidpay.ui.report;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.spidpay.R;
import com.example.spidpay.databinding.FragmentTransactionReportBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;

import org.jetbrains.annotations.NotNull;


public class Transaction_Report_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ChangeTitlenandIconInterface changeTitlenandIconInterface;
    FragmentTransactionReportBinding fragmentTransactionReportBinding;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface=(ChangeTitlenandIconInterface)context;
    }

    public Transaction_Report_Fragment() {
    }

    public static Transaction_Report_Fragment newInstance(String param1, String param2) {
        Transaction_Report_Fragment fragment = new Transaction_Report_Fragment();
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentTransactionReportBinding = FragmentTransactionReportBinding.inflate(inflater, container, false);
        return fragmentTransactionReportBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.report),true);

        fragmentTransactionReportBinding.rbPeriodLastMonth.setOnClickListener(v -> {
            boolean checked = ((RadioButton) v).isChecked();
            if (checked) {
                fragmentTransactionReportBinding.rbPeriodLast6month.setChecked(false);
                fragmentTransactionReportBinding.rbPeriodLast3month.setChecked(false);
                fragmentTransactionReportBinding.rbPeriodLastOneyear.setChecked(false);
            }
        });
        fragmentTransactionReportBinding.rbPeriodLast3month.setOnClickListener(v -> {
            boolean checked = ((RadioButton) v).isChecked();
            if (checked) {
                fragmentTransactionReportBinding.rbPeriodLastMonth.setChecked(false);
                fragmentTransactionReportBinding.rbPeriodLast6month.setChecked(false);
                fragmentTransactionReportBinding.rbPeriodLastOneyear.setChecked(false);
            }
        });

        fragmentTransactionReportBinding.rbPeriodLast6month.setOnClickListener(v -> {
            boolean checked = ((RadioButton) v).isChecked();
            if (checked) {
                fragmentTransactionReportBinding.rbPeriodLastMonth.setChecked(false);
                fragmentTransactionReportBinding.rbPeriodLast3month.setChecked(false);
                fragmentTransactionReportBinding.rbPeriodLastOneyear.setChecked(false);
            }
        });

        fragmentTransactionReportBinding.rbPeriodLastOneyear.setOnClickListener(v -> {
            boolean checked = ((RadioButton) v).isChecked();
            if (checked) {
                fragmentTransactionReportBinding.rbPeriodLastMonth.setChecked(false);
                fragmentTransactionReportBinding.rbPeriodLast6month.setChecked(false);
                fragmentTransactionReportBinding.rbPeriodLast3month.setChecked(false);
            }
        });
    }
}