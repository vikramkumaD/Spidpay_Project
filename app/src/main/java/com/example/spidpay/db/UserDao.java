package com.example.spidpay.db;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.spidpay.data.response.UserData;

@Dao
public interface UserDao {

    @Insert
    public long insertUser(UserData userData);
}
