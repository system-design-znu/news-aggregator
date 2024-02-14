package com.znu.news.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.znu.news.R;
import com.znu.news.databinding.ActivityMainBinding;
import com.znu.news.ui.base.BaseActivity;
import com.znu.news.utils.SessionManager;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);


        if (navHostFragment != null) {
            navHostFragment.getNavController().addOnDestinationChangedListener((navController, navDestination, bundle) -> {
                switch (navDestination.getId()) {
                    case R.id.homeFragment, R.id.searchFragment, R.id.settingFragment ->
                            showBottomAppbar();
                    default -> hideBottomAppbar();
                }
            });

            NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostFragment.getNavController());
        }

        observeData();
    }

    private void hideBottomAppbar() {
        binding.bottomAppbar.setVisibility(View.GONE);
    }

    private void showBottomAppbar() {
        binding.bottomAppbar.setVisibility(View.VISIBLE);
    }

    private void observeData() {
        sessionManager.authStatus.observe(this, status -> {
            if (status == SessionManager.AuthStatus.LOGGED_OUT) {
                openLoginActivity();
                finish();
            }
        });
    }
}