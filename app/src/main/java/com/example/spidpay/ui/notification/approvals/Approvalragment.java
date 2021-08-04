package com.example.spidpay.ui.notification.approvals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.example.spidpay.databinding.FragmentApprovalragmentBinding;

import org.jetbrains.annotations.NotNull;


public class Approvalragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    FragmentApprovalragmentBinding fragmentApprovalragmentBinding;

    public Approvalragment() {
    }

    public static Approvalragment newInstance(String param1, String param2) {
        Approvalragment fragment = new Approvalragment();
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
        fragmentApprovalragmentBinding = FragmentApprovalragmentBinding.inflate(inflater, container, false);
        return fragmentApprovalragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ItemOffsetDecoration itemOffsetDecoration=new ItemOffsetDecoration(getActivity(), R.dimen.marign10dp);
        fragmentApprovalragmentBinding.rvNotificationApprovals.addItemDecoration(itemOffsetDecoration);
        fragmentApprovalragmentBinding.rvNotificationApprovals.setLayoutManager(new LinearLayoutManager(getActivity()));
        Noti_Approval_Adapter noti_approval_adapter = new Noti_Approval_Adapter();
        fragmentApprovalragmentBinding.rvNotificationApprovals.setAdapter(noti_approval_adapter);
    }
}