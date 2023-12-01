package com.znu.news.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class AppPreferencesHelper {

    private static final String USER_ACCESS_TOKEN = "USER_ACCESS_TOKEN";
    private static final String USER_REFRESH_TOKEN = "USER_REFRESH_TOKEN";
    private static final String NIGHT_MODE = "NIGHT_MODE";


    private final SharedPreferences sharedPreferences;


    @Inject
    public AppPreferencesHelper(Context context, String prefFileName) {
        sharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    public String getAccessToken() {
        return sharedPreferences.getString(USER_ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken) {
        sharedPreferences.edit().putString(USER_ACCESS_TOKEN, accessToken).apply();
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(USER_REFRESH_TOKEN, null);
    }

    public void setRefreshToken(String refreshToken) {
        sharedPreferences.edit().putString(USER_REFRESH_TOKEN, refreshToken).apply();
    }

    public void clearAllTokens() {
        sharedPreferences.edit()
                .remove(USER_ACCESS_TOKEN)
                .remove(USER_REFRESH_TOKEN)
                .apply();
    }

    public int getNightMode() {
        return sharedPreferences.getInt(NIGHT_MODE, 1);
    }

    public void setNightMode(int nightMode) {
        sharedPreferences.edit().putInt(NIGHT_MODE, nightMode).apply();
    }
}
