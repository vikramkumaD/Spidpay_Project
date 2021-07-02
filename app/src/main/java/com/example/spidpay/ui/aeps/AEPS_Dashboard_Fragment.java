package com.example.spidpay.ui.aeps;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spidpay.R;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AEPS_Dashboard_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AEPS_Dashboard_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ChangeTitlenandIconInterface changeTitlenandIconInterface;

    public AEPS_Dashboard_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface = (ChangeTitlenandIconInterface) context;
    }

    public static AEPS_Dashboard_Fragment newInstance(String param1, String param2) {
        AEPS_Dashboard_Fragment fragment = new AEPS_Dashboard_Fragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_a_e_p_s__dashboard_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.Aeps), true);
    }
}