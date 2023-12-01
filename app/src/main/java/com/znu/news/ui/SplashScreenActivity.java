package com.znu.news.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.znu.news.databinding.ActivitySplashScreenBinding;
import com.znu.news.model.Error;
import com.znu.news.ui.base.BaseViewModelActivity;
import com.znu.news.ui.main.MainActivity;
import com.znu.news.utils.SessionManager;
import com.znu.news.viewmodel.SplashScreenViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends BaseViewModelActivity<ActivitySplashScreenBinding, SplashScreenViewModel> {

    @Override
    protected ActivitySplashScreenBinding initViewBinding() {
        return ActivitySplashScreenBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(SplashScreenViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.splashErrorTv.setOnClickListener(v -> viewModel.checkToken());

        observeData();
    }

    private void observeData() {
        viewModel.observeToken().observe(this, token -> {
            switch (token.status) {
                case LOADING:
                    binding.splashProgressbar.setVisibility(View.VISIBLE);
                    binding.splashErrorLayout.setVisibility(View.GONE);
                    break;

                case ERROR:
                    binding.splashProgressbar.setVisibility(View.GONE);
                    binding.splashErrorLayout.setVisibility(View.VISIBLE);
                    break;

                case SUCCESS:
                    String result = token.data;
                    if (!TextUtils.isEmpty(result))
                        sessionManager.login(result);
                    else sessionManager.logout();
                    break;
            }
        });

        sessionManager.authStatus.observe(this, status -> {
            if (status == SessionManager.AuthStatus.LOGGED_IN) {
                startActivity(toActivity(MainActivity.class));
            } else {
                openLoginActivity();
            }
            finish();
        });
    }
}