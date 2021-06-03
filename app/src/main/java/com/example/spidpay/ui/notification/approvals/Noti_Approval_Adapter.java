package com.example.spidpay.ui.notification.approvals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spidpay.R;
import com.example.spidpay.databinding.NotiApprovalsItemviewBinding;

import org.jetbrains.annotations.NotNull;

public class Noti_Approval_Adapter extends RecyclerView.Adapter<Noti_Approval_Adapter.NotiApprovalViewHolder>{

    public Noti_Approval_Adapter() {
    }

    @NonNull
    @NotNull
    @Override
    public NotiApprovalViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        NotiApprovalsItemviewBinding notiApprovalsItemviewBinding=  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.noti_approvals_itemview,parent,false);
        return new NotiApprovalViewHolder(notiApprovalsItemviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotiApprovalViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class NotiApprovalViewHolder extends RecyclerView.ViewHolder {
        NotiApprovalsItemviewBinding notiApprovalsItemviewBinding;
        public NotiApprovalViewHolder(NotiApprovalsItemviewBinding itemView) {
            super(itemView.getRoot());
            this.notiApprovalsItemviewBinding=itemView;
        }
    }
}
