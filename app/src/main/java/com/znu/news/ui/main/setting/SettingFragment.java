package com.znu.news.ui.main.setting;

import com.znu.news.R;
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
}