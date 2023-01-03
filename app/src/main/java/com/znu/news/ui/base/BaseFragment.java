package com.znu.news.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<B extends ViewBinding> extends Fragment {


    protected B binding;
    protected BaseActivity<?> activity;

    protected abstract B initViewBinding();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            activity = (BaseActivity<?>) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding();
        return binding.getRoot();
    }


    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    protected void openLoginActivity() {
        activity.openLoginActivity();
    }

    protected void navTo(int destenation) {
        Navigation.findNavController(binding.getRoot()).navigate(destenation);
    }

    protected void navTo(int destanation, Bundle bundle) {
        Navigation.findNavController(binding.getRoot()).navigate(destanation, bundle);
    }

    protected void navUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    public Intent toActivity(Class<?> destination) {
        return activity.toActivity(destination);
    }
}
