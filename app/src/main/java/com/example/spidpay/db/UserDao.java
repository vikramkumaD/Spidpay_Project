package com.example.spidpay.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.spidpay.data.response.UserData;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     long insertUser(UserData userData);


    @Query("SELECT * FROM UserData")
    UserData getAll();
}
