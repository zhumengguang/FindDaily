package com.lzx.zhihudaily.eitity;

import io.realm.RealmObject;

/**
 * Created by xian on 2016/10/23.
 */

public class CollectNews extends RealmObject {
    public String newsId;
    public String newsTitle;
    public String newsImage;

    public CollectNews() {
    }

    public CollectNews(String newsId, String newsTitle, String newsImage) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsImage = newsImage;
    }

    public String getNewsId() {
        return newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsImage() {
        return newsImage;
    }
}
