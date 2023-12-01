package com.znu.news.ui.main.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.R;
import com.znu.news.databinding.FragmentHomeFavoriteBinding;
import com.znu.news.model.News;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.ui.main.comn.NewsAdapter;
import com.znu.news.viewmodel.HomeViewModel;

import java.util.Arrays;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFavoriteFragment extends BaseViewModelFragment<FragmentHomeFavoriteBinding, HomeViewModel> implements NewsAdapter.NewsCallBack {

    private NewsAdapter favoriteNewsAdapter;

    public HomeFavoriteFragment() {
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(requireParentFragment()).get(HomeViewModel.class);
    }

    @Override
    protected FragmentHomeFavoriteBinding initViewBinding() {
        return FragmentHomeFavoriteBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpAdapter();
        observeData();
    }

    private void observeData() {
        viewModel.observeImportantNews().observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case LOADING:
                    break;

                case SUCCESS:
                    favoriteNewsAdapter.submitData(response.data);
                    break;
            }
        });
    }

    private void setUpAdapter() {
        favoriteNewsAdapter = new NewsAdapter(NewsAdapter.Card.FULL_IMAGE_CARD);
        favoriteNewsAdapter.setNewsCallBack(this);
        binding.favoriteNewsRv.setAdapter(favoriteNewsAdapter);
    }

    @Override
    public void onNewsClick(News news) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        navTo(R.id.action_to_newsDetailsFragment, bundle);
    }
}