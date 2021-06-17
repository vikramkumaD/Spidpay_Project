package com.example.spidpay.ui.signup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spidpay.R;
import com.example.spidpay.data.response.InterrestedforResponse;
import com.example.spidpay.databinding.InterrestedforItemviewBinding;
import com.example.spidpay.interfaces.OnClickIterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InterrestedforAdapter extends RecyclerView.Adapter<InterrestedforAdapter.InterrestedforViewHolder> {

    OnClickIterface onClickIterface;
    List<InterrestedforResponse> list;

    public InterrestedforAdapter(List<InterrestedforResponse> list, OnClickIterface onClickIterface) {
        this.list = list;
        this.onClickIterface = onClickIterface;
    }

    @NonNull
    @NotNull
    @Override
    public InterrestedforViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        InterrestedforItemviewBinding InterrestedforViewHolder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.interrestedfor_itemview, parent, false);
        return new InterrestedforViewHolder(InterrestedforViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InterrestedforViewHolder holder, int position) {
        holder.interrestedforItemviewBinding.setInterestedfor(list.get(position));
        holder.interrestedforItemviewBinding.setOninterestedforclick(onClickIterface);
        holder.interrestedforItemviewBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InterrestedforViewHolder extends RecyclerView.ViewHolder {
        InterrestedforItemviewBinding interrestedforItemviewBinding;

        public InterrestedforViewHolder(@NonNull @NotNull InterrestedforItemviewBinding itemView) {
            super(itemView.getRoot());
            interrestedforItemviewBinding = itemView;
        }
    }
}
