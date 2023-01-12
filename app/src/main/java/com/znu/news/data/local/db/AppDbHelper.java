package com.znu.news.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.znu.news.data.local.db.dao.UserDao;
import com.znu.news.data.local.db.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class AppDbHelper extends RoomDatabase {

    public abstract UserDao userDao();
}
