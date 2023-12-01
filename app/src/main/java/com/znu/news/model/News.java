package com.znu.news.model;

import java.io.Serializable;

public class News implements Serializable {

    private long id;
    private String category;
    private String title;
    private String description;
    private String author;
    private String pubDate;
    private String imageUrl;


    public News(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public News(String title, String pubDate, String author, String mediaUrl, String category, String description) {
        this.title = title;
        this.pubDate = pubDate;
        this.author = author;
        this.imageUrl = mediaUrl;
        this.category = category;
        this.description = description;
    }

    public News(long id, String title, String description, String author, String pubDate, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.pubDate = pubDate;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
