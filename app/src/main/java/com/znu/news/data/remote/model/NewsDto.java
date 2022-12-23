package com.znu.news.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsDto {

    @SerializedName("author")
    private String author;
    @SerializedName("categories")
    private List<String> categories;
    @SerializedName("content")
    private String content;
    @SerializedName("description")
    private String description;
    @SerializedName("enclosure")
    private Enclosure enclosure;
    @SerializedName("guid")
    private String guid;
    @SerializedName("link")
    private String link;
    @SerializedName("pubDate")
    private String pubDate;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("title")
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
