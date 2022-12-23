package com.znu.news.data.remote.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NewsResponse {

    @SerializedName("feed")
    @Expose
    private Feed feed;
    @SerializedName("items")
    @Expose
    private List<NewsDto> newsDtoList;
    @SerializedName("status")
    @Expose
    private String status;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<NewsDto> getNewsDtoList() {
        return newsDtoList;
    }

    public void setNewsDtoList(List<NewsDto> newsDtoList) {
        this.newsDtoList = newsDtoList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
