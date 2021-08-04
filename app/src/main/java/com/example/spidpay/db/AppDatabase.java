package com.example.spidpay.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spidpay.data.response.ParentUser;
import com.example.spidpay.data.response.UserInfo;


@Database(entities = {UserInfo.class, ParentUser.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}
