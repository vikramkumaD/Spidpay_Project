package com.example.spidpay.data.response;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ParentUser {
    @ColumnInfo(name = "firstName")
    public String firstName;
    @ColumnInfo(name = "lastName")
    public String lastName;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userId")
    public String userId;
    @ColumnInfo(name = "mobileNo")
    public String mobileNo;
}
