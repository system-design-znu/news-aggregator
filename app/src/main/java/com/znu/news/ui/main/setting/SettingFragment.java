package com.znu.news.ui.main.setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.znu.news.BuildConfig;
import com.znu.news.databinding.FragmentSettingBinding;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.viewmodel.SettingViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingFragment extends BaseViewModelFragment<FragmentSettingBinding, SettingViewModel> {


    public SettingFragment() {
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected FragmentSettingBinding initViewBinding() {
        return FragmentSettingBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.releaseTextView.setText(BuildConfig.VERSION_NAME);
    }
}