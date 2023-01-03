package com.znu.news.ui.main.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.AppBarLayout;
import com.znu.news.R;
import com.znu.news.databinding.FragmentNewsDetailsBinding;
import com.znu.news.di.GlideApp;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.viewmodel.NewsDetailsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewsDetailsFragment extends BaseViewModelFragment<FragmentNewsDetailsBinding, NewsDetailsViewModel> {


    @Override
    protected FragmentNewsDetailsBinding initViewBinding() {
        return FragmentNewsDetailsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(NewsDetailsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        activity.getWindow()
                .setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                );


        GlideApp.with(this)
                .load(R.drawable.image)
                .centerCrop()
                .into(binding.newsImageView);

        setUpToolBar();
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpToolBar() {
        binding.newsDetailsToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.share_news_item) {
                //TODO: share link to news
                return true;
            }
            return false;
        });

        binding.newsDetailsAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
//                    getBaseActivity().getWindow().setStatusBarColor(getBaseActivity().getResources().getColor(R.color.black));
                } else {
//                    getBaseActivity().getWindow().setStatusBarColor(getBaseActivity().getResources().getColor(R.color.black_100));
                }
            }
        });

        binding.newsDetailsToolbar.setNavigationOnClickListener(v -> navUp());
    }

    @Override
    public void onDestroyView() {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onDestroyView();
    }
}