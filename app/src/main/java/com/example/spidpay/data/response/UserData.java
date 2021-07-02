package com.example.spidpay.data.response;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class UserData {
    @NonNull
    @PrimaryKey
    public String userId;

    public String firstName;

    public String lastName;

    public String username;

    public String domainCode;

    public String accountStatus;

    public String group;

    public String userType;

    public String userScope;

    public String parentUserID;

    public String whiteLabelUserID;

    @Ignore
    @SerializedName("roles")
    public List<Roles> rolesList;

    @Ignore
    @SerializedName("permissions")
    public List<String> permissions;


    @Ignore
    @SerializedName("parentUser")
    ParentUser parentUser;

}

