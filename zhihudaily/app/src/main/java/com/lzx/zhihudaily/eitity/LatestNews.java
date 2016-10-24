package com.lzx.zhihudaily.eitity;

import java.util.List;

/**
 * Created by lzx on 2016/10/21.
 * 功能：最新消息，过往消息（没有top_stories字段）
 */

public class LatestNews {
    public String date;
    public List<Stories> stories;
    public List<TopStories> top_stories;

    public String getDate() {
        return date;
    }

    public List<Stories> getStories() {
        return stories;
    }

    public List<TopStories> getTop_stories() {
        return top_stories;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }
}
