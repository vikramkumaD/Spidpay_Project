package com.example.spidpay.ui.tradewallet;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spidpay.R;
import com.example.spidpay.data.request.BankResponse;
import com.example.spidpay.data.response.InterestedResponse;
import com.example.spidpay.databinding.InterrestedforItemviewBinding;
import com.example.spidpay.databinding.UserBankListBinding;
import com.example.spidpay.interfaces.OnUserBankClick;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankListViewHolder> {

    List<BankResponse> list;
    OnUserBankClick onUserBankClick;

    public BankListAdapter(List<BankResponse> list,OnUserBankClick onUserBankClick) {
        this.list = list;
        this.onUserBankClick=onUserBankClick;

    }

    @NonNull
    @NotNull
    @Override
    public BankListAdapter.BankListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        UserBankListBinding InterrestedforViewHolder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.user_bank_list, parent, false);
        return new BankListAdapter.BankListViewHolder(InterrestedforViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BankListAdapter.BankListViewHolder holder, int position) {
        holder.interrestedforItemviewBinding.setBankResponse(list.get(position));
        holder.interrestedforItemviewBinding.setOnuserbankclick(onUserBankClick);
        holder.interrestedforItemviewBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BankListViewHolder extends RecyclerView.ViewHolder {
        UserBankListBinding interrestedforItemviewBinding;

        public BankListViewHolder(@NonNull @NotNull UserBankListBinding itemView) {
            super(itemView.getRoot());
            interrestedforItemviewBinding = itemView;
        }
    }
}

