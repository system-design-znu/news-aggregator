package com.znu.news.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.znu.news.data.remote.CallbackWrapper;
import com.znu.news.data.repo.UserRepository;
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
    private final UserRepository userRepository;

    @Inject
    public SplashScreenViewModel(Application application
            , SchedulerProvider schedulerProvider
            , UserRepository userRepository) {
        super(application, schedulerProvider);
        this.userRepository = userRepository;

        token = new MutableLiveData<>();

        checkToken();
    }

    public void checkToken() {
        if (isConnected()) {
            token.setValue(Resource.loading());
            userRepository.checkToken()
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(new CallbackWrapper<String, Error>() {
                        @Override
                        protected void onComplete(String s) {
                            token.setValue(Resource.success(s));
                        }

                        @Override
                        protected void onFailure(Error e) {
                            token.setValue(Resource.error(e));
                        }
                    });
        } else token.setValue(Resource.error(new Error.RemoteServiceError(ErrorType.Connection)));
    }

    public MutableLiveData<Resource<String>> observeToken() {
        return token;
    }
}
