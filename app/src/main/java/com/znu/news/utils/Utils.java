package com.znu.news.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

public final class Utils {

    public static final int FIRST_PAGE = 1;
    public static final int NEWS_PER_PAGE = 10;

    public static final long SWIPE_REFRESH_TIME = 1500;


    public static String getErrorMessage(Context context, String name) {
        String packageName = "com.znu.news";
        Resources resources = context.getResources();
        @SuppressLint("DiscouragedApi")
        int resId = resources.getIdentifier(name, "string", packageName);
        return resources.getString(resId);
    }
}
