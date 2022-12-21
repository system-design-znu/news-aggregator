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
import com.znu.news.model.News;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.ui.main.comn.NewsAdapter;
import com.znu.news.utils.Utils;
import com.znu.news.viewmodel.HomeViewModel;

import java.util.Arrays;

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
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.homeMainRl.setOnRefreshListener(() -> {
            viewModel.loadData();

            new Handler().postDelayed(
                    (Runnable) () -> {
                        binding.homeMainRl.setRefreshing(false);
                    }, Utils.SWIPE_REFRESH_TIME
            );
        });

        setUpAdapters();

        new Handler().postDelayed((Runnable) this::observeData, 2000);
    }

    private void setUpAdapters() {
        trendingNewsAdapter = new NewsAdapter();

        popularNewsAdapter = new NewsAdapter();
        binding.popularNewsRv.setAdapter(popularNewsAdapter);
        binding.popularShimmerFl.setVisibility(View.VISIBLE);
        binding.popularShimmerFl.startShimmer();

        importantNewsAdapter = new NewsAdapter();
        binding.importantNewsRv.setAdapter(importantNewsAdapter);
        binding.importantShimmerFl.setVisibility(View.VISIBLE);
        binding.importantShimmerFl.startShimmer();
    }

    private void observeData() {
        observeTrendingNews();
        observePopularNews();
        observeImportantNews();
    }

    private void observeImportantNews() {
        //call from api
//        viewModel.observeImportantNews().observe(getViewLifecycleOwner(), response -> {
//            switch (response.status) {
//                case LOADING:
//                    binding.popularNewsProgressbar.setVisibility(View.VISIBLE);
//                    break;
//
//                case ERROR:
//                    binding.popularNewsProgressbar.setVisibility(View.GONE);
//                    handleError(response.error);
//                    break;
//
//                case SUCCESS:
//                    break;
//            }
//        });
        importantNewsAdapter.submitData(Arrays.asList(new News(), new News(), new News()));
        binding.importantShimmerFl.setVisibility(View.GONE);
        binding.importantShimmerFl.stopShimmer();
    }

    private void observePopularNews() {
        //call from api
//        viewModel.observePopularNews().observe(getViewLifecycleOwner(), response -> {
//            switch (response.status) {
//                case LOADING:
//                    binding.popularNewsProgressbar.setVisibility(View.VISIBLE);
//                    break;
//
//                case ERROR:
//                    binding.popularNewsProgressbar.setVisibility(View.GONE);
//                    handleError(response.error);
//                    break;
//
//                case SUCCESS:
//                    break;
//            }
//        });
        popularNewsAdapter.submitData(Arrays.asList(new News(), new News(), new News()));
        binding.popularShimmerFl.setVisibility(View.GONE);
        binding.popularShimmerFl.stopShimmer();
    }

    private void observeTrendingNews() {
        viewModel.observeTrendingNews().observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case LOADING:
                    break;

                case ERROR:
                    handleError(response.error);
                    break;

                case SUCCESS:
                    Toast.makeText(activity, "SUCCESS error", Toast.LENGTH_SHORT).show();
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