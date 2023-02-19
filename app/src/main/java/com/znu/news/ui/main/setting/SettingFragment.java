package com.znu.news.ui.main.setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.BuildConfig;
import com.znu.news.databinding.FragmentSettingBinding;
import com.znu.news.model.User;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.viewmodel.SettingViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingFragment extends BaseViewModelFragment<FragmentSettingBinding, SettingViewModel> {


    public SettingFragment() {
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(SettingViewModel.class);
    }

    @Override
    protected FragmentSettingBinding initViewBinding() {
        return FragmentSettingBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeData();
        setUpViews();
    }

    private void setUpViews() {
        binding.nightModeSc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.nightModeSc.setEnabled(false);
            activity.overridePendingTransition(0, 0);
            viewModel.setNightMode((isChecked) ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            activity.recreate();
            binding.nightModeSc.setEnabled(true);
        });
    }

    private void observeData() {

        binding.releaseTextView.setText(BuildConfig.VERSION_NAME);
        binding.nightModeSc.setChecked(viewModel.getNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

        viewModel.observeUser().observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case SUCCESS:
                    User user = response.data;
                    binding.fullNameTv.setText(user.getName());
                    binding.emailTextView.setText(user.getEmail());
                    binding.passwordTextView.setText(user.getPassword());
                    break;
            }
        });
    }
}