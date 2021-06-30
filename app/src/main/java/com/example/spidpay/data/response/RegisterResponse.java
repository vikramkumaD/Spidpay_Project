package com.example.spidpay.data.response;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class RegisterResponse {

    @PrimaryKey
    public String userId;
    @ColumnInfo(name = "firstName")
    public String firstName;
    public String lastName, username, domainCode, accountStatus, group, whiteLabelUserID, firstTimeLogin, userScope, userType;

    @SerializedName("parentUser")
    public ParentUser parentUser;

    @SerializedName("roles")
    public List<Roles> rolesList;

    @SerializedName("permissions")
    public List<String> permissionlist;
}
