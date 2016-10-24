package com.lzx.zhihudaily.eitity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lzx on 2016/10/21.
 * 功能：消息内容获取
 */

public class NewsDetail {
    public String body;
    @SerializedName("image_source")
    public String imageSource; // 图片的内容提供方
    public String title;
    public String image; //获得的图片同 最新消息 获得的图片分辨率不同。这里获得的是在文章浏览界面中使用的大图。
    @SerializedName("share_url")
    public String shareUrl; // 供在线查看内容与分享至 SNS 用的 URL
    public List<String> js; //供手机端的 WebView(UIWebView) 使用
    @SerializedName("ga_prefix")
    public String gaPrefix;  //供 Google Analytics 使用
    public List<String> images;
    public int type;
    public String id;
    public List<String> css; //供手机端的 WebView(UIWebView) 使用

    public String getBody() {
        return body;
    }

    public String getImageSource() {
        return imageSource;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public List<String> getJs() {
        return js;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public List<String> getImages() {
        return images;
    }

    public int getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public List<String> getCss() {
        return css;
    }
}
