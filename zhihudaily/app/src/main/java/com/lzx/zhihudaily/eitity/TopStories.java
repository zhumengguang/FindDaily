package com.lzx.zhihudaily.eitity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lzx on 2016/10/21.
 * 功能：界面顶部 ViewPager 滚动显示的显示内容（子项格式同上）（请注意区分此处的 image 属性与 stories 中的 images 属性）
 */

public class TopStories {
    public String image;
    public int type;
    public String id;
    @SerializedName("ga_prefix")
    public String gaPrefix;
    public String title;

    public String getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public String getTitle() {
        return title;
    }
}
