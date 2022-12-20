package com.znu.news.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.znu.news.R;
import com.znu.news.databinding.ActivityMainBinding;
import com.znu.news.model.Error;
import com.znu.news.ui.base.BaseViewModelActivity;
import com.znu.news.viewmodel.NewsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseViewModelActivity<ActivityMainBinding, NewsViewModel> {

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        observeTrendingNews();
    }

    private void observeTrendingNews() {
        viewModel.observeTrendingNews().observe(this, response -> {
            switch (response.status) {
                case LOADING:
                    Toast.makeText(this, "LOADING error", Toast.LENGTH_SHORT).show();
                    break;

                case ERROR:
                    handleError(response.error);
                    break;

                case SUCCESS:
                    Toast.makeText(this, "SUCCESS error", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void handleError(Error error) {
        switch (error.errorType) {
            case Connection:
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
                break;

            case Unauthorized:
                Toast.makeText(this, "Unauthorized error", Toast.LENGTH_SHORT).show();
                openLoginActivity();
                break;

            case Unknown:
                Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}