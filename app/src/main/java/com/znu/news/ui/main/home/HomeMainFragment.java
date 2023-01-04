package com.znu.news.ui.main.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.R;
import com.znu.news.databinding.FragmentHomeMainBinding;
import com.znu.news.model.Error;
import com.znu.news.model.News;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.ui.main.comn.NewsAdapter;
import com.znu.news.viewmodel.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeMainFragment extends BaseViewModelFragment<FragmentHomeMainBinding, HomeViewModel> implements NewsAdapter.NewsCallBack {


    private NewsAdapter trendingNewsAdapter;
    private NewsAdapter popularNewsAdapter;
    private NewsAdapter importantNewsAdapter;

    public HomeMainFragment() {
    }

    @Override
    protected FragmentHomeMainBinding initViewBinding() {
        return FragmentHomeMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(requireParentFragment()).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpAdapters();
        observeData();
    }

    private void setUpAdapters() {
        trendingNewsAdapter = new NewsAdapter(NewsAdapter.Card.TRENDING_CARD);
        trendingNewsAdapter.setNewsCallBack(this);
        binding.trendingNewsRv.setAdapter(trendingNewsAdapter);

        popularNewsAdapter = new NewsAdapter();
        popularNewsAdapter.setNewsCallBack(this);
        binding.popularNewsRv.setAdapter(popularNewsAdapter);

        importantNewsAdapter = new NewsAdapter();
        importantNewsAdapter.setNewsCallBack(this);
        binding.importantNewsRv.setAdapter(importantNewsAdapter);
    }

    private void observeData() {
        observeTrendingNews();
        observePopularNews();
        observeImportantNews();
    }

    private void observeImportantNews() {
        viewModel.observeImportantNews().observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case LOADING:
                    binding.importantShimmerFl.setVisibility(View.VISIBLE);
                    binding.importantShimmerFl.startShimmer();
                    break;

                case SUCCESS:
                    importantNewsAdapter.submitData(response.data.subList(4, 7));
                    binding.importantShimmerFl.setVisibility(View.GONE);
                    binding.importantShimmerFl.stopShimmer();
                    break;
            }
        });
    }

    private void observePopularNews() {
        viewModel.observePopularNews().observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case LOADING:
                    binding.popularShimmerFl.setVisibility(View.VISIBLE);
                    binding.popularShimmerFl.startShimmer();
                    break;

                case SUCCESS:
                    popularNewsAdapter.submitData(response.data.subList(0, 3));
                    binding.popularShimmerFl.setVisibility(View.GONE);
                    binding.popularShimmerFl.stopShimmer();
                    break;
            }
        });
    }

    private void observeTrendingNews() {
        viewModel.observeTrendingNews().observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case LOADING:
                    binding.trendingShimmerFl.setVisibility(View.VISIBLE);
                    binding.trendingShimmerFl.startShimmer();
                    break;

                case SUCCESS:
                    trendingNewsAdapter.submitData(response.data.subList(5, 10));
                    binding.trendingShimmerFl.setVisibility(View.GONE);
                    binding.trendingShimmerFl.stopShimmer();
                    break;
            }
        });
    }

    @Override
    public void onNewsClick(News news) {
        Bundle bundle = new Bundle();
        bundle.putInt("newsId", news.getId());
        navTo(R.id.action_to_newsDetailsFragment, bundle);
    }
}