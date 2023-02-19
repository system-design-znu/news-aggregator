package com.znu.news.data.remote.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NewsResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("page_size")
    private int pageSize;

    @SerializedName("content_1")
    @Expose
    private NewsDto content_1;

    @SerializedName("content_2")
    @Expose
    private NewsDto content_2;

    @SerializedName("content_3")
    @Expose
    private NewsDto content_3;

    @SerializedName("content_4")
    @Expose
    private NewsDto content_4;

    @SerializedName("content_5")
    @Expose
    private NewsDto content_5;

    @SerializedName("content_6")
    @Expose
    private NewsDto content_6;

    @SerializedName("content_7")
    @Expose
    private NewsDto content_7;

    @SerializedName("content_8")
    @Expose
    private NewsDto content_8;

    @SerializedName("content_9")
    @Expose
    private NewsDto content_9;

    @SerializedName("content_10")
    @Expose
    private NewsDto content_10;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public NewsDto getContent_1() {
        return content_1;
    }

    public void setContent_1(NewsDto content_1) {
        this.content_1 = content_1;
    }

    public NewsDto getContent_2() {
        return content_2;
    }

    public void setContent_2(NewsDto content_2) {
        this.content_2 = content_2;
    }

    public NewsDto getContent_3() {
        return content_3;
    }

    public void setContent_3(NewsDto content_3) {
        this.content_3 = content_3;
    }

    public NewsDto getContent_4() {
        return content_4;
    }

    public void setContent_4(NewsDto content_4) {
        this.content_4 = content_4;
    }

    public NewsDto getContent_5() {
        return content_5;
    }

    public void setContent_5(NewsDto content_5) {
        this.content_5 = content_5;
    }

    public NewsDto getContent_6() {
        return content_6;
    }

    public void setContent_6(NewsDto content_6) {
        this.content_6 = content_6;
    }

    public NewsDto getContent_7() {
        return content_7;
    }

    public void setContent_7(NewsDto content_7) {
        this.content_7 = content_7;
    }

    public NewsDto getContent_8() {
        return content_8;
    }

    public void setContent_8(NewsDto content_8) {
        this.content_8 = content_8;
    }

    public NewsDto getContent_9() {
        return content_9;
    }

    public void setContent_9(NewsDto content_9) {
        this.content_9 = content_9;
    }

    public NewsDto getContent_10() {
        return content_10;
    }

    public void setContent_10(NewsDto content_10) {
        this.content_10 = content_10;
    }
}
