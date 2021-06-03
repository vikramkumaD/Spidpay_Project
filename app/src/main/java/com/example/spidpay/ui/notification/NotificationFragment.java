package com.example.spidpay.ui.notification;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;
import com.example.spidpay.ui.notification.alerts.AlertFragment;
import com.example.spidpay.ui.notification.approvals.Approvalragment;
import com.example.spidpay.ui.profile.ProfileFragmentTab;
import com.example.spidpay.ui.profile.bankgst.BankGSTFragment;
import com.example.spidpay.ui.profile.kyc.KYCFragment;
import com.example.spidpay.ui.profile.profile.MyProfileFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;


public class NotificationFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private static final int NUM_PAGES = 2;
    private final String[] titles = new String[]{"ALERTS", "APPROVALS"};
    ChangeTitlenandIconInterface changeTitlenandIconInterface;

    public NotificationFragment() {
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface=(ChangeTitlenandIconInterface)context;
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
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.notification),true);
        FragmentStateAdapter pagerAdapter = new MyPagerAdapter(getActivity());
        ViewPager2 viewPager=view.findViewById(R.id.viewPager_notification);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout=view.findViewById(R.id.tabLayout_notification);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(titles[position])).attach();
    }

    private static class MyPagerAdapter extends FragmentStateAdapter {
        public MyPagerAdapter(FragmentActivity fa) {
            super(fa);
        }
        @NotNull
        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return AlertFragment.newInstance("fragment 1", "");
                }
                case 1: {
                    return Approvalragment.newInstance("fragment 2", "");
                }
                default:
                    return AlertFragment.newInstance("fragment 1", "");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}