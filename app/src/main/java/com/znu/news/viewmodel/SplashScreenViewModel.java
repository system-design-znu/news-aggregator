package com.znu.news.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.znu.news.data.local.prefs.AppPreferencesHelper;
import com.znu.news.model.Error;
import com.znu.news.model.ErrorType;
import com.znu.news.model.Resource;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SplashScreenViewModel extends BaseViewModel {

    private final MutableLiveData<Resource<String>> token;
    private final AppPreferencesHelper appPreferencesHelper;

    @Inject
    public SplashScreenViewModel(Application application
            , SchedulerProvider schedulerProvider
            , AppPreferencesHelper appPreferencesHelper) {
        super(application, schedulerProvider);

        this.appPreferencesHelper = appPreferencesHelper;

        token = new MutableLiveData<>();

        checkToken();
    }

    public void checkToken() {
        if (isConnected()) {
            token.setValue(Resource.loading());

            token.setValue(Resource.success(appPreferencesHelper.getAccessToken()));
        } else token.setValue(Resource.error(new Error.RemoteServiceError(ErrorType.Connection)));
    }

    public MutableLiveData<Resource<String>> observeToken() {
        return token;
    }
}
