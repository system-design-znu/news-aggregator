package com.znu.news.di;


import android.content.Context;

import androidx.room.Room;

import com.znu.news.BuildConfig;
import com.znu.news.data.local.db.AppDbHelper;
import com.znu.news.data.local.db.dao.UserDao;
import com.znu.news.data.local.prefs.AppPreferencesHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LocalModule {

    @Provides
    @Named("PREF_NAME")
    String providePreferenceName() {
        return BuildConfig.PREF_NAME;
    }

    @Provides
    AppPreferencesHelper provideAppPreferencesHelper(Context context, @Named("PREF_NAME") String PREF_NAME) {
        return new AppPreferencesHelper(
                context,
                PREF_NAME
        );
    }

    @Provides
    @Named("DATABASE_NAME")
    String provideDatabaseName() {
        return BuildConfig.DATABASE_NAME;
    }

    @Provides
    @Singleton
    AppDbHelper provideAppDataBase(Context context, @Named("DATABASE_NAME") String DATABASE_NAME) {
        return Room.databaseBuilder(
                        context
                        , AppDbHelper.class
                        , DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    UserDao provideTaskDao(AppDbHelper appDbHelper) {
        return appDbHelper.userDao();
    }
}
