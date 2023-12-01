package com.znu.news.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class NewsDto {

    private long id;
    private String agency;
    private String description;
    private String title;
    @SerializedName("publish_date")
    private String publishDate;
    private String url;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("last_update_date")
    private String lastUpdateDate;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String value) {
        this.agency = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String value) {
        this.lastUpdateDate = value;
    }
}
