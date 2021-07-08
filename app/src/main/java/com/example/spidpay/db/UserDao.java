package com.example.spidpay.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.spidpay.data.response.ParentUser;
import com.example.spidpay.data.response.UserData;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(UserData userData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertParent(ParentUser parentUser);

    @Query("SELECT * FROM UserData")
    UserData getUser();

    @Query("select * from ParentUser")
    ParentUser getParent();
}
