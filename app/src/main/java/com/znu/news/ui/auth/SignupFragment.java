package com.znu.news.ui.auth;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.databinding.FragmentSignupBinding;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.ui.main.MainActivity;
import com.znu.news.viewmodel.AuthViewModel;

public class SignupFragment extends BaseViewModelFragment<FragmentSignupBinding, AuthViewModel> {

    @Override
    protected FragmentSignupBinding initViewBinding() {
        return FragmentSignupBinding.inflate(getLayoutInflater());

    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(activity).get(AuthViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signUpButton.setOnClickListener(v -> {
            startActivity(toActivity(MainActivity.class));
            activity.finish();
        });
    }
}