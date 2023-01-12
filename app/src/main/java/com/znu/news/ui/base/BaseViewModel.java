package com.znu.news.ui.base;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.AndroidViewModel;

import com.znu.news.utils.rx.SchedulerProvider;

public abstract class BaseViewModel extends AndroidViewModel {

    protected final SchedulerProvider schedulerProvider;

    public BaseViewModel(Application application, SchedulerProvider schedulerProvider) {
        super(application);
        this.schedulerProvider = schedulerProvider;
    }


    protected boolean isConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && (info.isConnected() || (info.isRoaming()));
    }
}
