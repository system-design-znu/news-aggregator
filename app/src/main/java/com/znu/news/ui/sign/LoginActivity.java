package com.znu.news.ui.sign;

import androidx.lifecycle.ViewModelProvider;

import com.znu.news.R;
import com.znu.news.databinding.ActivityLoginBinding;
import com.znu.news.ui.base.BaseViewModelActivity;
import com.znu.news.viewmodel.UserViewModel;

public class LoginActivity extends BaseViewModelActivity<ActivityLoginBinding, UserViewModel> {


    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}