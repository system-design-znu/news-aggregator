package com.znu.news.ui.main.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.R;
import com.znu.news.databinding.FragmentNewsDetailsBinding;
import com.znu.news.di.GlideApp;
import com.znu.news.model.News;
import com.znu.news.ui.base.BaseViewModelFragment;
import com.znu.news.utils.Utils;
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

        if (getArguments() != null) {
            viewModel.setNews((News) getArguments().getSerializable("news"));//TODO:delete this part later.
        }

        activity.getWindow()
                .setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                );

        binding.newsDetailsSv.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(0).getBottom() <= v.getMeasuredHeight() + scrollY) {
                if (binding.reactionsLayout.getTranslationY() > 0) {
                    binding.reactionsLayout.animate().setDuration(400).translationY(0);
                }
            }
        });

        setUpToolBar();
        observeData();
    }

    private void observeData() {
        viewModel.observeNews().observe(getViewLifecycleOwner(), news -> {
            GlideApp.with(this)
                    .load(R.drawable.image)//TODO:replace with news image
                    .centerCrop()
                    .into(binding.newsImageView);

            binding.newsTitleTv.setText(news.getTitle());
            binding.newsAuthorTv.setText(news.getAuthor());
            binding.newsDescriptionTv.setText(news.getDescription());
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpToolBar() {
        binding.newsDetailsAppbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.newsDetailsCl.getLayoutParams();
            if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                binding.toolbarDividerView.setVisibility(View.VISIBLE);
                params.topMargin = (int) Utils.dipToPx(activity, 16);
            } else if (binding.toolbarDividerView.getVisibility() == View.VISIBLE) {
                binding.toolbarDividerView.setVisibility(View.GONE);
                params.topMargin = 0;
            }
            binding.newsDetailsCl.setLayoutParams(params);
        });

        binding.newsDetailsToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.share_news_item) {
                //TODO: share link to news
                return true;
            }
            return false;
        });

        binding.newsDetailsToolbar.setNavigationOnClickListener(v -> navUp());
    }

    @Override
    public void onDestroyView() {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onDestroyView();
    }
}