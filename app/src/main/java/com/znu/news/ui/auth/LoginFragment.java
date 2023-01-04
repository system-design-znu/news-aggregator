package com.znu.news.ui.auth;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.R;
import com.znu.news.databinding.FragmentLoginBinding;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.ui.main.MainActivity;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signUpTextView.setOnClickListener(v -> navTo(R.id.action_loginFragment_to_signupFragment));
        binding.loginButton.setOnClickListener(v -> {
            startActivity(toActivity(MainActivity.class));
            activity.finish();
        });
    }
}