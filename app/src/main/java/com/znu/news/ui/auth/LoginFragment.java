package com.znu.news.ui.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
            String username = binding.emailEt.getText().toString();
            String password = binding.passWordEt.getText().toString();

            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                viewModel.login(username, password);
            }
        });

        viewModel.observeUserToken().observe(getViewLifecycleOwner(), userToken -> {
            binding.userProgressBarLayout.setVisibility(View.GONE);
            switch (userToken.status) {
                case LOADING:
                    binding.userProgressBarLayout.setVisibility(View.VISIBLE);
                    break;

                case ERROR:
                    Log.d("LoginFragment", "onViewCreated: " + userToken.error.error);
                    break;

                case SUCCESS:
                    activity.sessionManager.login(userToken.data.getAccessToken(), userToken.data.getRefreshToken());
            }
        });
    }
}