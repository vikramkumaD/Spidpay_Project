package com.example.spidpay.data.response;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ParentUser {
    @PrimaryKey
    @NonNull
    public String userId;
    public String firstName,lastName,mobileNo;
}
