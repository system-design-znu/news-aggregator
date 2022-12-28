package com.znu.news.ui.sign;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.R;
import com.znu.news.databinding.ActivityLoginBinding;
import com.znu.news.ui.base.BaseViewModelActivity;
import com.znu.news.ui.main.MainActivity;
import com.znu.news.utils.SessionManager;
import com.znu.news.viewmodel.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends BaseViewModelActivity<ActivityLoginBinding, UserViewModel> {


    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    protected ActivityLoginBinding initViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        observeData();
    }

    private void observeData() {
        sessionManager.status.observe(this, status -> {
            if (status == SessionManager.Status.LOGGED_IN) {
                startActivity(toActivity(MainActivity.class));
            }
        });
    }
}