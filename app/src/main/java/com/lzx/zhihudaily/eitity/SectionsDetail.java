package com.lzx.zhihudaily.eitity;

import java.util.List;

/**
 * Created by lzx on 2016/10/21.
 * 功能：栏目具体消息
 */

public class SectionsDetail {
    public long timestamp; //时间戳
    public List<Stories> stories;
    public String name;

    public long getTimestamp() {
        return timestamp;
    }

    public List<Stories> getStories() {
        return stories;
    }

    public String getName() {
        return name;
    }
}
