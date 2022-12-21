package com.znu.news.ui.main.comn;

import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.znu.news.R;
import com.znu.news.databinding.NewsItemBinding;
import com.znu.news.model.News;
import com.znu.news.ui.base.BaseAdapter;
import com.znu.news.ui.base.BaseViewHolder;

public class NewsAdapter extends BaseAdapter<News> {


    @Override
    protected BaseViewHolder<News, ?> initViewHolder(ViewGroup parent) {
        return new NewsViewHolder(NewsItemBinding.inflate(layoutInflater, parent, false));
    }

    public static class NewsViewHolder extends BaseViewHolder<News, NewsItemBinding> {

        public NewsViewHolder(NewsItemBinding itemBinding) {
            super(itemBinding);
        }

        @Override
        public void bind(News news) {
            Glide.with(itemView)
                    .load(R.drawable.image)
                    .into(itemBinding.newsImageView);
        }
    }
}
