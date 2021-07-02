package com.example.spidpay.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spidpay.data.response.UserData;


@Database(entities = {UserData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
}