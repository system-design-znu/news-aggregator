package com.znu.news.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.znu.news.databinding.ActivitySplashScreenBinding;
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

        new Handler().postDelayed(this::observeData, 1000);
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
                    String t = token.data;
                    if (!t.equals("null"))
                        sessionManager.login(t);
                    else sessionManager.logout();
                    break;
            }
        });

        sessionManager.status.observe(this, status -> {
            if (status == SessionManager.Status.LOGGED_IN) {
                startActivity(toActivity(MainActivity.class));
            } else {
                openLoginActivity();
            }
            finish();
        });
    }
}