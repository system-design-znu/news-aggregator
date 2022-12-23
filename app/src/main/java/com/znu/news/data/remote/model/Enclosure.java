
package com.znu.news.data.remote.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Enclosure {

    @SerializedName("link")
    private String link;
    @SerializedName("type")
    private String type;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
