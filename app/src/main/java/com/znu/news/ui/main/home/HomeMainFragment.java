package com.znu.news.ui.main.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.R;
import com.znu.news.databinding.FragmentHomeMainBinding;
import com.znu.news.model.Error;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.ui.main.comn.NewsAdapter;
import com.znu.news.utils.Utils;
import com.znu.news.viewmodel.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeMainFragment extends BaseViewModelFragment<FragmentHomeMainBinding, HomeViewModel> {


    private NewsAdapter trendingNewsAdapter;
    private NewsAdapter popularNewsAdapter;
    private NewsAdapter importantNewsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_main;
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(requireParentFragment()).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.homeMainRl.setOnRefreshListener(() -> {

            new Handler().postDelayed(
                    () -> {
                        binding.homeMainRl.setRefreshing(false);
                    }, Utils.SWIPE_REFRESH_TIME
            );
        });

        setUpAdapters();
        observeData();
    }

    private void setUpAdapters() {
        trendingNewsAdapter = new NewsAdapter(true);
        binding.trendingNewsRv.setAdapter(trendingNewsAdapter);

        popularNewsAdapter = new NewsAdapter();
        binding.popularNewsRv.setAdapter(popularNewsAdapter);

        importantNewsAdapter = new NewsAdapter();
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

                case ERROR:
                    binding.importantShimmerFl.setVisibility(View.GONE);
                    binding.importantShimmerFl.stopShimmer();
                    handleError(response.error);
                    break;

                case SUCCESS:
                    importantNewsAdapter.submitData(response.data.subList(0, 3));
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

                case ERROR:
                    binding.popularShimmerFl.setVisibility(View.GONE);
                    binding.popularShimmerFl.stopShimmer();
                    handleError(response.error);
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

                case ERROR:
                    binding.trendingShimmerFl.setVisibility(View.GONE);
                    binding.trendingShimmerFl.stopShimmer();
                    handleError(response.error);
                    break;

                case SUCCESS:
                    trendingNewsAdapter.submitData(response.data.subList(0, 5));
                    binding.trendingShimmerFl.setVisibility(View.GONE);
                    binding.trendingShimmerFl.stopShimmer();
                    break;
            }
        });
    }

    private void handleError(Error error) {
        switch (error.errorType) {
            case Connection:
                Toast.makeText(activity, "Connection error", Toast.LENGTH_SHORT).show();
                break;

            case Unauthorized:
                Toast.makeText(activity, "Unauthorized error", Toast.LENGTH_SHORT).show();
                openLoginActivity();
                break;

            case Unknown:
                Toast.makeText(activity, "Unknown error", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}