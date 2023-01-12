package com.znu.news.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.znu.news.data.repo.UserRepository;
import com.znu.news.model.Error;
import com.znu.news.model.Resource;
import com.znu.news.model.User;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingViewModel extends BaseViewModel {

    private final MutableLiveData<Resource<User>> user;
    private final UserRepository userRepository;

    @Inject
    public SettingViewModel(Application application
            , SchedulerProvider schedulerProvider
            , UserRepository userRepository) {
        super(application, schedulerProvider);
        this.userRepository = userRepository;

        user = new MutableLiveData<>();

//        this.userRepository.insertUser(new User(
//                        "محمد هادی سرمیلی"
//                        , "m@gmail.com"
//                        , "1234"
//                )).subscribeOn(schedulerProvider.io())
//                .subscribe();

        loadData();
    }

    private void loadData() {
        fetchUserData();
    }

    private void fetchUserData() {
        userRepository.getUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnNext(user -> this.user.setValue(Resource.success(user)))
                .doOnError(throwable -> user.setValue(Resource.error(new Error(throwable))))
                .subscribe();
    }

    public int getNightMode() {
        return userRepository.getNightMode();
    }

    public void setNightMode(int nightMode) {
        userRepository.setNightMode(nightMode);
    }

    public MutableLiveData<Resource<User>> observeUser() {
        return user;
    }
}
