package com.znu.news.viewmodel;

import com.znu.news.data.repo.UserRepository;
import com.znu.news.ui.base.BaseViewModel;
import com.znu.news.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserViewModel extends BaseViewModel {

    private UserRepository userRepository;

    @Inject
    public UserViewModel(SchedulerProvider schedulerProvider, UserRepository userRepository) {
        super(schedulerProvider);
        this.userRepository = userRepository;
    }
}
