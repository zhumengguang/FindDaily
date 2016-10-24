package com.lzx.zhihudaily.eitity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lzx on 2016/10/21.
 * 功能：热门消息
 */

public class Recent {
    @SerializedName("news_id")
    public String newsId;
    public String url;
    public String thumbnail;
    public String title;

    public String getNewsId() {
        return newsId;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }
}
