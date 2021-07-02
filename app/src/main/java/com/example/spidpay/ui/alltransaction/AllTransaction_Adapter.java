package com.example.spidpay.ui.alltransaction;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spidpay.R;
import com.example.spidpay.databinding.AlltransactionItemviewBinding;
import org.jetbrains.annotations.NotNull;


public class AllTransaction_Adapter extends RecyclerView.Adapter<AllTransaction_Adapter.AllTransactionViewHolder> {


    @NonNull
    @NotNull
    @Override
    public AllTransactionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        AlltransactionItemviewBinding alltransactionItemviewBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.alltransaction_itemview,parent,false);
        return new AllTransactionViewHolder(alltransactionItemviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllTransactionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public static class AllTransactionViewHolder extends RecyclerView.ViewHolder {
        final AlltransactionItemviewBinding alltransactionItemviewBinding;
        public AllTransactionViewHolder(@NonNull @NotNull AlltransactionItemviewBinding itemView) {
            super(itemView.getRoot());
            this.alltransactionItemviewBinding=itemView;
        }
    }
}
