package com.znu.news.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class NewsDto {

    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private String author;
    @SerializedName("descriptions")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
