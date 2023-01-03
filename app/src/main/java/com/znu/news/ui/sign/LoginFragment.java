package com.znu.news.ui.sign;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.znu.news.R;
import com.znu.news.databinding.FragmentLoginBinding;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.viewmodel.AuthViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseViewModelFragment<FragmentLoginBinding, AuthViewModel> {

    @Override
    protected FragmentLoginBinding initViewBinding() {
        return FragmentLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(activity).get(AuthViewModel.class);
    }
}