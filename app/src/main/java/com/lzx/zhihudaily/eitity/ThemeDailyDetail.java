package com.lzx.zhihudaily.eitity;

import java.util.List;

/**
 * Created by lzx on 2016/10/21.
 * 功能：主题日报内容
 */

public class ThemeDailyDetail {
    public List<Stories> stories; //该主题日报中的文章列表
    public String description; //该主题日报的介绍
    public String background; //该主题日报的背景图片（大图）
    public String color;
    public String name;
    public String image;  //背景图片的小图版本
    public List<Editor> editors; //该主题日报的编辑（『用户推荐日报』中此项的指是一个空数组，在 App 中的主编栏显示为『许多人』，点击后访问该主题日报的介绍页面，请留意）
    public String image_source; //图像的版权信息

    public List<Stories> getStories() {
        return stories;
    }

    public String getDescription() {
        return description;
    }

    public String getBackground() {
        return background;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public String getImage_source() {
        return image_source;
    }
}
