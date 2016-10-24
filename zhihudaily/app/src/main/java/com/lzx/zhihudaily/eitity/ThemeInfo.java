package com.lzx.zhihudaily.eitity;

import java.util.List;

/**
 * Created by lzx on 2016/10/21.
 * 功能：主题日报列表查看
 */

public class ThemeInfo {
    public int limit;

    public List<ThemeDaily> subscribed; // 已订阅条目
    public List<ThemeDaily> others; //其他条目

    public int getLimit() {
        return limit;
    }

    public List<ThemeDaily> getSubscribed() {
        return subscribed;
    }

    public List<ThemeDaily> getOthers() {
        return others;
    }


}
