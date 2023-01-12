package com.znu.news.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.znu.news.data.local.db.entity.UserEntity;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface UserDao {

    @Insert
    Completable insert(UserEntity user);

    @Delete
    Completable delete(UserEntity user);

    @Update
    Completable update(UserEntity user);

    @Query("SELECT * FROM USERENTITY LIMIT 1")
    Flowable<UserEntity> getUser();
}
