package com.znu.news.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;

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

    public static float dipToPx(Context context, int dip) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
    }

    public static String convertDateToShamsi(String stringDate) {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.US);
        Date date;
        try {
            date = sourceFormat.parse(stringDate);

            long currentTime = System.currentTimeMillis();
            long difference = currentTime - (date != null ? date.getTime() : 0);
            if (difference < 0) {
                return stringDate;
            } else if (difference < 60_000) {
                int seconds = (int) (difference / 1_000);
                return seconds + " ثانیه پیش";
            } else if (difference < 3_600_000) {
                int minutes = (int) (difference / 60_000);
                return minutes + " دقیقه پیش";
            } else if (difference < 86_400_000) {
                int hours = (int) (difference / 3_600_000);
                return hours + " ساعت پیش";
            } else if (difference < 2_592_000_000L) {
                int days = (int) (difference / 86_400_000);
                return days + " روز پیش";
            } else {
                SimpleDateFormat pDateFormat = new SimpleDateFormat("d MMM yyyy", new Locale("fa"));
                return pDateFormat.format(date);
            }
        } catch (Exception e) {
            return stringDate;
        }
    }

    public static RequestBody createBody(Object object) {
        Gson gson = new Gson();
        String requestBody = gson.toJson(object); // Replace with your request body object

        return RequestBody.create(MediaType.parse("application/json"), requestBody);
    }
}
