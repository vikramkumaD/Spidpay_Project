package com.example.spidpay.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponse {

    public String userId, firstName, lastName, username, domainCode, accountStatus, group, whiteLabelUserID, firstTimeLogin, userScope, userType;

    @SerializedName("parentUser")
    public ParentUser parentUser;

    @SerializedName("roles")
    public List<Roles> rolesList;

    @SerializedName("permissions")
    public List<String> permissionlist;
}
