package com.znu.news.model;

import java.io.Serializable;

public class News implements Serializable {

    private int id;
    private String title;
    private String pubDate;
    private String author;
    private String mediaUrl;
    private String category;
    private String description;


    public News(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public News(String title, String pubDate, String author, String mediaUrl, String category) {
        this.title = title;
        this.pubDate = pubDate;
        this.author = author;
        this.mediaUrl = mediaUrl;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
