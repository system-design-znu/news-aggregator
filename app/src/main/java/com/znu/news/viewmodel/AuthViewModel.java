package com.znu.news.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.znu.news.data.remote.CallbackWrapper;
import com.znu.news.data.repo.UserRepository;
import com.znu.news.model.Error;
import com.znu.news.model.Resource;
import com.znu.news.model.User;
import com.znu.news.model.UserToken;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.Utils;
import com.znu.news.utils.rx.SchedulerProvider;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

@HiltViewModel
public class AuthViewModel extends BaseViewModel {

    private final MutableLiveData<Resource<List<String>>> categories;
    private final MutableLiveData<Resource<UserToken>> userToken;

    private final UserRepository userRepository;


    @Inject
    public AuthViewModel(Application application
            , SchedulerProvider schedulerProvider
    , UserRepository userRepository) {
        super(application, schedulerProvider);
        this.userRepository = userRepository;

        categories = new MutableLiveData<>();
        userToken = new MutableLiveData<>();

        observeData();
    }

    public void login(String username, String password) {
        userToken.setValue(Resource.loading());
        userRepository.login(Utils.createBody(new User(username, password)))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<UserToken>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UserToken token) {
                        userToken.setValue(Resource.success(token));
                    }

                    @Override
                    public void onError(Throwable e) {
                        userToken.setValue(Resource.error(new Error(e)));
                    }
                });
    }

    private void observeData() {
        categories.setValue(Resource.success(Arrays.asList("#تکنولوژی", "#تکنولوژی", "#سیاسی", "#ورزشی", "#تکنولوژی", "#سیاسی", "#ورزشی", "#تکنولوژی", "#سیاسی", "#ورزشی")));
    }

    public MutableLiveData<Resource<List<String>>> observeCategories() {
        return categories;
    }

    public MutableLiveData<Resource<UserToken>> observeUserToken() {
        return userToken;
    }
}
