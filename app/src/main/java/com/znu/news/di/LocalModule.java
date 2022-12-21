package com.znu.news.di;


import android.content.Context;

import com.znu.news.BuildConfig;
import com.znu.news.data.local.prefs.AppPreferencesHelper;

import javax.inject.Named;

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
    AppPreferencesHelper provideAppPreferencesHelper(Context context, @Named("PREF_NAME") String preferenceName) {
        return new AppPreferencesHelper(
                context,
                preferenceName
        );
    }
}
