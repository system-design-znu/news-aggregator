package com.znu.news.ui.main.comn;

import android.util.Log;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.znu.news.databinding.FavoriteNewsItemBinding;
import com.znu.news.databinding.NewsItemBinding;
import com.znu.news.databinding.TrendingNewsItemBinding;
import com.znu.news.di.GlideApp;
import com.znu.news.model.News;
import com.znu.news.ui.base.BaseAdapter;
import com.znu.news.ui.base.BaseViewHolder;
import com.znu.news.utils.Utils;

public class NewsAdapter extends BaseAdapter<News> {


    private final Card card;
    private NewsCallBack newsCallBack;

    public NewsAdapter() {
        card = Card.NORMAL_CARD;
    }

    public NewsAdapter(Card card) {
        this.card = card;
    }

    @Override
    protected BaseViewHolder<News, ?> initViewHolder(ViewGroup parent) {
        if (card == Card.FULL_IMAGE_CARD)
            return new FavoriteNewsViewHolder(
                    FavoriteNewsItemBinding.inflate(
                            layoutInflater,
                            parent,
                            false
                    )
            );
        else if (card == Card.TRENDING_CARD)
            return new TrendingNewsViewHolder(
                    TrendingNewsItemBinding.inflate(
                            layoutInflater,
                            parent,
                            false
                    )
            );
        return new NewsViewHolder(
                NewsItemBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                )
        );
    }

    public void setNewsCallBack(NewsCallBack newsCallBack) {
        this.newsCallBack = newsCallBack;
    }

    public enum Card {
        NORMAL_CARD,
        FULL_IMAGE_CARD,
        TRENDING_CARD
    }

    public interface NewsCallBack {
        void onNewsClick(News news);
    }

    public class NewsViewHolder extends BaseViewHolder<News, NewsItemBinding> {

        public NewsViewHolder(NewsItemBinding itemBinding) {
            super(itemBinding);
        }

        @Override
        public void bind(News news) {
            itemBinding.newsTitleTv.setText(news.getTitle());
            itemBinding.newsPubDateTv.setText(Utils.convertDateToShamsi(news.getPubDate()));
            itemBinding.newsAuthorTv.setText(news.getAuthor());
            itemBinding.newsCategoryTv.setText(news.getCategory());

            itemBinding.getRoot().setOnClickListener(v -> {
                if (newsCallBack != null) newsCallBack.onNewsClick(news);
            });

            GlideApp.with(itemView.getContext())
                    .load(news.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(itemBinding.newsImageView);
        }
    }

    public class TrendingNewsViewHolder extends BaseViewHolder<News, TrendingNewsItemBinding> {

        public TrendingNewsViewHolder(TrendingNewsItemBinding itemBinding) {
            super(itemBinding);
        }

        @Override
        public void bind(News news) {
            itemBinding.newsTitleTv.setText(news.getTitle());
            itemBinding.newsPubDateTv.setText(Utils.convertDateToShamsi(news.getPubDate()));
            itemBinding.newsAuthorTv.setText(news.getAuthor());

            itemBinding.getRoot().setOnClickListener(v -> {
                if (newsCallBack != null) newsCallBack.onNewsClick(news);
            });

            GlideApp.with(itemView.getContext())
                    .load(news.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(itemBinding.newsImageView);
        }
    }

    public class FavoriteNewsViewHolder extends BaseViewHolder<News, FavoriteNewsItemBinding> {

        public FavoriteNewsViewHolder(FavoriteNewsItemBinding itemBinding) {
            super(itemBinding);
        }

        @Override
        public void bind(News news) {
            itemBinding.newsTitleTv.setText(news.getTitle());
            itemBinding.newsPubDateTv.setText(Utils.convertDateToShamsi(news.getPubDate()));
            itemBinding.newsAuthorTv.setText(news.getAuthor());
            itemBinding.newsCategoryTv.setText(news.getCategory());

            itemBinding.getRoot().setOnClickListener(v -> {
                if (newsCallBack != null) newsCallBack.onNewsClick(news);
            });

            GlideApp.with(itemView.getContext())
                    .load(news.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(itemBinding.newsImageView);
        }
    }
}
