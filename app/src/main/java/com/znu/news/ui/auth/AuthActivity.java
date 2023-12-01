package com.znu.news.ui.auth;

import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.znu.news.R;
import com.znu.news.databinding.ActivityAuthBinding;
import com.znu.news.ui.base.BaseActivity;
import com.znu.news.ui.main.MainActivity;
import com.znu.news.utils.SessionManager;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthActivity extends BaseActivity<ActivityAuthBinding> {

    private NavHostFragment navHostFragment;


    @Override
    protected ActivityAuthBinding initViewBinding() {
        return ActivityAuthBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);


        sessionManager.authStatus.observe(this, status -> {
            if (status == SessionManager.AuthStatus.LOGGED_IN) {
                startActivity(toActivity(MainActivity.class));
                finish();
            }
        });
    }
}