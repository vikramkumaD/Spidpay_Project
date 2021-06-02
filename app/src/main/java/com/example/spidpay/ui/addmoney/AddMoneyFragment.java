package com.example.spidpay.ui.addmoney;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.spidpay.R;
import com.example.spidpay.databinding.FragmentAddMoneyBinding;
import com.example.spidpay.databinding.LandingfragmentBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMoneyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMoneyFragment extends Fragment {

    FragmentAddMoneyBinding fragmentAddMoneyBinding;
    ChangeTitlenandIconInterface changeTitlenandIconInterface;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddMoneyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMoneyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMoneyFragment newInstance(String param1, String param2) {
        AddMoneyFragment fragment = new AddMoneyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface=(ChangeTitlenandIconInterface) context;
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
        // Inflate the layout for this fragment
        fragmentAddMoneyBinding = FragmentAddMoneyBinding.inflate(inflater, container, false);
        View root = fragmentAddMoneyBinding.getRoot();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentAddMoneyBinding.rbOnline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fragmentAddMoneyBinding.rbCashdesposit.setChecked(false);
            }
        });
        fragmentAddMoneyBinding.rbCashdesposit.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fragmentAddMoneyBinding.rbOnline.setChecked(false);
            }
        });
        fragmentAddMoneyBinding.btnChooseoptionformoney.setOnClickListener(v -> {
            if(fragmentAddMoneyBinding.rbOnline.isChecked())
            {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.available_Online_Balance_Fragment);
            }
            else if(fragmentAddMoneyBinding.rbCashdesposit.isChecked()){
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.cash_Desposit_Fragment);
            }
            else {
                Toast.makeText(getActivity(),"choose one option!",Toast.LENGTH_SHORT).show();
            }
        });

        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.addmoney),true);
    }

}