package com.example.spidpay.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login_UserData {
    public String userId,firstName,lastName,username,domainCode,accountStatus,group,parentUserID,whiteLabelUserID;

    @SerializedName("roles")
    public List<Roles> rolesList;

  /*  @SerializedName("permissions")
    public List<Permissions> permissions;*/

    @SerializedName("parentUser")
    ParentUser parentUser;
}

