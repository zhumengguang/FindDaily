package com.lzx.zhihudaily.eitity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lzx on 2016/10/21.
 * 功能：新闻的推荐者
 */

public class Recommender {
    public List<Editor> editors;
    @SerializedName("item_count")
    public int itemCount;

    public List<Editor> getEditors() {
        return editors;
    }

    public int getItemCount() {
        return itemCount;
    }
}
