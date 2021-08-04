package com.example.spidpay.ui.alltransaction;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spidpay.R;
import com.example.spidpay.data.response.AllTransactionResponse;
import com.example.spidpay.databinding.AlltransactionItemviewBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllTransactionAdapter extends RecyclerView.Adapter<AllTransactionAdapter.AllTransactionItemview> {

    List<AllTransactionResponse> list;
    public AllTransactionAdapter(List<AllTransactionResponse> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public AllTransactionItemview onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        AlltransactionItemviewBinding allTransactionItemviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.alltransaction_itemview, parent, false);
        return new AllTransactionItemview(allTransactionItemviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllTransactionItemview holder, int position) {
        holder.allTransactionItemviewBinding.setAlltransaction(list.get(position));
        holder.allTransactionItemviewBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AllTransactionItemview extends RecyclerView.ViewHolder {
        AlltransactionItemviewBinding allTransactionItemviewBinding;

        public AllTransactionItemview(@NonNull AlltransactionItemviewBinding itemView) {
            super(itemView.getRoot());
            allTransactionItemviewBinding = itemView;
        }
    }
}
