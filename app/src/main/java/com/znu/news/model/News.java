package com.znu.news.model;

public class News {

    private int id;
    private String title;
    private String pubDate;
    private String author;
    private String mediaUrl;
    private String category;
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
}
