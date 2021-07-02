package com.example.spidpay.ui.requestwallettransfer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spidpay.R;
import com.example.spidpay.databinding.FragmentRequestWalletTransferBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Request_Wallet_Transfer_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Request_Wallet_Transfer_Fragment extends Fragment {

    ChangeTitlenandIconInterface changeTitlenandIconInterface;
    FragmentRequestWalletTransferBinding requestWalletTransferBinding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Request_Wallet_Transfer_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Request_Wallet_Transfer_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Request_Wallet_Transfer_Fragment newInstance(String param1, String param2) {
        Request_Wallet_Transfer_Fragment fragment = new Request_Wallet_Transfer_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface = (ChangeTitlenandIconInterface) context;
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
        requestWalletTransferBinding = FragmentRequestWalletTransferBinding.inflate(inflater, container, false);
        return requestWalletTransferBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestWalletTransferBinding.rbParentname.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                requestWalletTransferBinding.rbSpeedpaywalletname.setChecked(false);
            }
        });
        requestWalletTransferBinding.rbSpeedpaywalletname.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                requestWalletTransferBinding.rbParentname.setChecked(false);
            }
        });
        requestWalletTransferBinding.btnChooseoptionformoney.setOnClickListener(v -> {
            if (requestWalletTransferBinding.rbParentname.isChecked()) {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.transfer_Money_Fragment);
            } else if (requestWalletTransferBinding.rbSpeedpaywalletname.isChecked()) {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.transfer_Money_Fragment);
            } else {
                Toast.makeText(getActivity(), "choose one option!", Toast.LENGTH_SHORT).show();
            }
        });

        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.requestwallettransafer), true);
    }
}