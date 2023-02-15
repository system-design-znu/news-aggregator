package com.znu.news.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.znu.news.model.Resource;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends BaseViewModel {

    private final MutableLiveData<Resource<List<String>>> categories;

    @Inject
    public AuthViewModel(Application application
            , SchedulerProvider schedulerProvider) {
        super(application, schedulerProvider);
        categories = new MutableLiveData<>();

        observeData();
    }

    private void observeData() {
        categories.setValue(Resource.success(Arrays.asList("#تکنولوژی", "#تکنولوژی", "#سیاسی", "#ورزشی", "#تکنولوژی", "#سیاسی", "#ورزشی", "#تکنولوژی", "#سیاسی", "#ورزشی")));
    }

    public MutableLiveData<Resource<List<String>>> observeCategories() {
        return categories;
    }
}
