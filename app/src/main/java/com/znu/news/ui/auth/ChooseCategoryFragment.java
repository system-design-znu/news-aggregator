package com.znu.news.ui.auth;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.databinding.FragmentChooseCategoryBinding;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.ui.main.MainActivity;
import com.znu.news.viewmodel.AuthViewModel;


public class ChooseCategoryFragment extends BaseViewModelFragment<FragmentChooseCategoryBinding, AuthViewModel> {

    private CategoryAdapter categoryAdapter;

    @Override
    protected FragmentChooseCategoryBinding initViewBinding() {
        return FragmentChooseCategoryBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(activity).get(AuthViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.confirmButton.setOnClickListener(v -> {
            startActivity(toActivity(MainActivity.class));
            activity.sessionManager.login("1");
            activity.finish();
        });

        setUpAdapter();
        observeData();
    }

    private void setUpAdapter() {
        categoryAdapter = new CategoryAdapter();
        binding.categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    private void observeData() {
        viewModel.observeCategories().observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case LOADING:
                    binding.categoryProgressbar.setVisibility(View.VISIBLE);
                    break;

                case SUCCESS:
                    categoryAdapter.submitData(response.data);
                    binding.categoryProgressbar.setVisibility(View.GONE);
                    break;
            }
        });
    }
}